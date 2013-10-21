package com.khipu.khpdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javax.ejb.Stateless;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Stateless
public class PdfGenerator implements PdfGeneratorRemote {

    @Override
    public byte[] pdfFromHtml(byte[] html, Map<PdfFields,String> fields) {
        try {
            System.out.println("............................hit");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, os);

            document.open();
            for (PdfFields key: fields.keySet()) {
                switch (key){
                    case SUBJECT:
                        document.addSubject(fields.get(key));
                        break;
                    case TITLE:
                        document.addTitle(fields.get(key));
                        break;
                    case AUTHOR:
                        document.addAuthor(fields.get(key));
                        break;
                    case CREATOR:
                        document.addCreator(fields.get(key));
                }
            }


            InputStream is = new ByteArrayInputStream(html);

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);

            document.close();

            return os.toByteArray();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //throw new NullPointerException("blabla");
        }
        return null;

    }
}
