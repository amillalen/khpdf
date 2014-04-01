package com.khipu.khpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodePDF417;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.exceptions.RuntimeWorkerException;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PdfGeneratorImpl implements PdfGenerator, PdfGeneratorRemote {

    private XMLWorkerHelper xmlWorkerHelper;

    @PostConstruct
    public void postConstruct() {
        xmlWorkerHelper = XMLWorkerHelper.getInstance();
    }

    @Override
    public byte[] pdfFromHtmlAndPdf417(byte[] html, Map<PdfFields,String> fields, PDF417Data pdf417data) throws DocumentGenerationException {
        ByteArrayInputStream in = new ByteArrayInputStream(pdfFromHtml(html,fields));
        try {
            BarcodePDF417 pdf417 = new BarcodePDF417();
            pdf417.setCodeRows(pdf417data.getCodeRows());
            pdf417.setCodeColumns(pdf417data.getCodeCols());
            pdf417.setErrorLevel(pdf417data.getErrorLevel());
            pdf417.setLenCodewords(pdf417data.getCodewordsLen());
            pdf417.setAspectRatio(pdf417data.getAspectRadio());
            pdf417.setOptions(BarcodePDF417.PDF417_FORCE_BINARY);
            pdf417.setText(pdf417data.getData());

            Image pdf417Image = Image.getInstance(pdf417.getImage());

            byte[] white= {(byte)255};
            Image quietZoneImg = Image.getInstance(1, 1, 1, 8, white);
            quietZoneImg.scaleAbsolute(pdf417Image.getWidth() + pdf417data.getQuietZoneWidth() * 2, pdf417Image.getHeight());

            quietZoneImg.setAbsolutePosition(pdf417data.getAbsoluteXPosition(),
                    pdf417data.getAbsoluteYPosition());


            pdf417Image.setAbsolutePosition(pdf417data.getAbsoluteXPosition() + pdf417data.getQuietZoneWidth(),
                    pdf417data.getAbsoluteYPosition());


            pdf417Image.setIndentationLeft(pdf417data.getQuietZoneWidth());
            pdf417Image.setIndentationRight(pdf417data.getQuietZoneWidth());


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfReader reader = new PdfReader(in);
            PdfStamper stamper = new PdfStamper(reader, out);
            stamper.getOverContent(1).addImage(quietZoneImg);
            stamper.getOverContent(1).addImage(pdf417Image);

            stamper.close();
            return out.toByteArray();

        } catch (IOException|DocumentException e) {
            throw new DocumentGenerationException(e);
        }

    }

    @Override
    public byte[] pdfFromHtml(byte[] html, Map<PdfFields,String> fields) throws DocumentGenerationException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
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
            xmlWorkerHelper.parseXHtml(writer, document, is);
            document.close();
            return os.toByteArray();

        } catch (DocumentException | IOException | RuntimeWorkerException e) {
            throw new DocumentGenerationException(e);
        }
    }
}
