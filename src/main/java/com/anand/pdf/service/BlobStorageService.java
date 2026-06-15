package com.anand.pdf.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlobStorageService {

    public List<File> loadDocuments(
            Long requestId)
            throws Exception {

        Path folder =
                Paths.get(
                        "src/main/resources/mock-data/request-"
                                + requestId);

        return Files.list(folder)
                .filter(f ->
                        f.toString()
                                .endsWith(".pdf"))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }
}