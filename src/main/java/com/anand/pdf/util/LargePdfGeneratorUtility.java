package com.anand.pdf.util;

import com.anand.pdf.create.LargePdfGenerator;

public class LargePdfGeneratorUtility {

    public static void main(String args[]) throws Exception {
        for(int i=1;i<=20;i++) {

            LargePdfGenerator.createLargeTextPdf(
                    100,
                    "src/main/resources/mock-data/request-1001/doc"
                            + i
                            + ".pdf");
        }
    }
}
