package com.anand.pdf.service;

import lombok.Value;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Service
public class CompressionService {
    //@Value("${ghostscript.path}")
    private String ghostscriptPath = "C:\\Program Files\\gs\\gs10.07.1\\bin\\gswin64c.exe";
    public File compress(File input)
            throws Exception {

        File output =
                File.createTempFile(
                        "compressed_",
                        ".pdf");

        ProcessBuilder pb =
                new ProcessBuilder(

                        ghostscriptPath,

                        "-sDEVICE=pdfwrite",

                        "-dCompatibilityLevel=1.4",

                        "-dPDFSETTINGS=/ebook",

                        "-dNOPAUSE",

                        "-dQUIET",

                        "-dBATCH",

                        "-sOutputFile="
                                + output.getAbsolutePath(),

                        input.getAbsolutePath());

        pb.redirectErrorStream(true);

        Process process = pb.start();

        //---------------------------------------------------
        // Ghostscript Log Monitoring
        //---------------------------------------------------

        Thread logThread =
                new Thread(() -> {

                    try {

                        BufferedReader reader =
                                new BufferedReader(

                                        new InputStreamReader(
                                                process.getInputStream()));

                        String line;

                        while ((line = reader.readLine())
                                != null) {

                            System.out.println(
                                    "[GS] " + line);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                });

        logThread.start();

        //---------------------------------------------------
        // File Size Monitoring
        //---------------------------------------------------

        while (process.isAlive()) {

            if (output.exists()) {

                long sizeMB =
                        output.length()
                                / 1024
                                / 1024;

                System.out.println(
                        "Compressed Size = "
                                + sizeMB
                                + " MB");
            }

            Thread.sleep(5000);
        }

        //---------------------------------------------------
        // Wait for Completion
        //---------------------------------------------------

        int rc = process.waitFor();

        System.out.println(
                "Ghostscript Exit Code = "
                        + rc);

        //---------------------------------------------------
        // Output Validation
        //---------------------------------------------------

        if (!output.exists()) {

            throw new RuntimeException(
                    "Compressed PDF not created");
        }

        try {

            PDDocument validationDoc =
                    PDDocument.load(output, MemoryUsageSetting.setupTempFileOnly());

            System.out.println(
                    "PDF Validation Successful");

            validationDoc.close();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Generated PDF is corrupted",
                    ex);
        }

        return output;
    }
}