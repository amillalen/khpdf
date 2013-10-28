package com.khipu.khpdf;

import javax.ejb.Local;
import java.util.Map;

@Local
public interface PdfGenerator {

    public byte[] pdfFromHtml(byte[] html, Map<PdfFields,String> fields) throws DocumentGenerationException;

}
