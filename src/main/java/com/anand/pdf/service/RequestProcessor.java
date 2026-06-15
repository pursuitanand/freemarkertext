package com.anand.pdf.service;

import com.anand.pdf.model.RequestJob;
import com.anand.pdf.util.MemoryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestProcessor {

    private final BlobStorageService storage;
    private final PdfMergeService mergeService;
    private final FooterService footerService;
    private final CompressionService compressionService;

    public void process(
            RequestJob job)
            throws Exception {
        MemoryUtil.print(
                "Before Footer");
        System.out.println(
                "Processing "
                        + job.getRequestId());

        List<File> files =
                storage.loadDocuments(
                        job.getRequestId());

        File merged =
                mergeService.merge(files);

        File footerPdf =
                footerService.applyFooter(
                        merged,
                        "CONFIDENTIAL");
        long start =
                System.currentTimeMillis();
        File compressed =
                compressionService.compress(footerPdf);
        long end =
                System.currentTimeMillis();
        System.out.println(
                "Compression Time="
                        + ((end-start)/1000)
                        + " sec");
        Path output =
                Paths.get(
                        "output",
                        "request-"
                                + job.getRequestId()
                                + ".pdf");

        Files.copy(
                compressed.toPath(),
                output,
                StandardCopyOption.REPLACE_EXISTING);
        MemoryUtil.print(
                "After Footer");
        System.out.println(
                "Completed "
                        + job.getRequestId());
    }
}