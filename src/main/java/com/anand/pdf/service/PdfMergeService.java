package com.anand.pdf.service;

import com.anand.pdf.util.MemoryUtil;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PdfMergeService {

    public File merge(
            List<File> sourceFiles)
            throws Exception {
        MemoryUtil.print(
                "Before Merge");
        File merged =
                File.createTempFile(
                        "merged_",
                        ".pdf");

        PDFMergerUtility merger =
                new PDFMergerUtility();

        for(File file : sourceFiles) {

            merger.addSource(file);
        }
        System.out.println(
                merged.getAbsolutePath());
        merger.setDestinationFileName(
                merged.getAbsolutePath());

        merger.mergeDocuments(
                MemoryUsageSetting
                        .setupTempFileOnly());
        MemoryUtil.print(
                "After Merge");
        return merged;
    }
}
