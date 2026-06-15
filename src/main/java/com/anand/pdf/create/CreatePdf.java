package com.anand.pdf.create;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
public class CreatePdf {
    public static void createPdf(
            String text,
            String output)
            throws Exception {

        PDDocument doc =
                new PDDocument();

        PDPage page =
                new PDPage();

        doc.addPage(page);

        PDPageContentStream cs =
                new PDPageContentStream(
                        doc,
                        page);

        cs.beginText();

        cs.setFont(
                PDType1Font.HELVETICA_BOLD,
                20);

        cs.newLineAtOffset(
                100,
                700);

        cs.showText(text);

        cs.endText();

        cs.close();

        doc.save(output);

        doc.close();
    }

//createPdf(
//    "PDF 1",
//            "mock-data/request-1001/doc1.pdf");
//
//createPdf(
//    "PDF 2",
//            "mock-data/request-1001/doc2.pdf");
//
//createPdf(
//    "PDF 3",
//            "mock-data/request-1001/doc3.pdf");

}