package com.anand.pdf.service;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;

@Service
public class FooterService {

    public File applyFooter(
            File pdf,
            String footerText)
            throws Exception {

        PDDocument doc =
                PDDocument.load(pdf, MemoryUsageSetting.setupTempFileOnly());

        for(PDPage page :
                doc.getPages()) {

            PDRectangle box =
                    page.getMediaBox();

            PDPageContentStream cs =
                    new PDPageContentStream(
                            doc,
                            page,
                            PDPageContentStream.AppendMode.APPEND,
                            true);

            cs.setNonStrokingColor(
                    Color.WHITE);

            cs.addRect(
                    0,
                    0,
                    box.getWidth(),
                    30);

            cs.fill();

            cs.beginText();

            cs.setNonStrokingColor(
                    Color.BLACK);

            cs.setFont(
                    PDType1Font.HELVETICA,
                    8);

            cs.newLineAtOffset(
                    50,
                    10);

            cs.showText(
                    footerText);

            cs.endText();

            cs.close();
        }

        File output =
                File.createTempFile(
                        "footer_",
                        ".pdf");

        doc.save(output);

        doc.close();

        return output;
    }
}