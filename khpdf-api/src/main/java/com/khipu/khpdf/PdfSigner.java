package com.khipu.khpdf;

import javax.ejb.Local;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Map;

@Local
public interface PdfSigner {

    public byte[] signPdf(byte[] pdfData, PrivateKey pk, Certificate[] chain, Map<PdfSignatureFields, String> params)
            throws DocumentGenerationException;

}
