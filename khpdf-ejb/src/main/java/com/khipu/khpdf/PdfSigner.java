package com.khipu.khpdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.ejb.Stateless;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Map;

@Stateless
public class PdfSigner implements PdfSignerRemote {

    @Override
    public byte[] signPdf(byte[] pdfData, PrivateKey pk, Certificate[] chain, Map<PdfSignatureFields, String> params) {

        try {
            PdfReader reader = new PdfReader(pdfData);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper pdfStamper = PdfStamper.createSignature(reader, bos, '\0');
            PdfSignatureAppearance appearance = pdfStamper.getSignatureAppearance();
            for (PdfSignatureFields key : params.keySet()) {
                switch (key) {
                    case LOCATION:
                        appearance.setLocation(params.get(key));
                        break;
                    case REASON:
                        appearance.setReason(params.get(key));

                }
            }
            appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);

            ExternalDigest digest = new BouncyCastleDigest();
            ExternalSignature signature = new PrivateKeySignature(pk, DigestAlgorithms.SHA1, BouncyCastleProvider.PROVIDER_NAME);
            MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);

            pdfStamper.close();
            bos.close();
            reader.close();
            return bos.toByteArray();

        } catch (IOException | GeneralSecurityException | DocumentException e) {
            return null;
        }
    }

}
