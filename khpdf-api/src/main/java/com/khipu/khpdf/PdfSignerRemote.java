package com.khipu.khpdf;


import javax.ejb.Remote;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Map;

@Remote
public interface PdfSignerRemote {

    public byte[] signPdf(byte[] pdfData, PrivateKey pk, Certificate[] chain, Map<PdfSignatureFields, String> params);
}
