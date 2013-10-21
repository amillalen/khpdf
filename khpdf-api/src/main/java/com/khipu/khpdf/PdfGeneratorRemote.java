package com.khipu.khpdf;

import javax.ejb.Remote;
import java.util.Map;

@Remote
public interface PdfGeneratorRemote {

    public byte[] pdfFromHtml(byte[] html, Map<PdfFields,String> fields);
}
