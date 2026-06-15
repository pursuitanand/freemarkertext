package com.anand.pdf.model;

import java.io.File;

public class DocumentBlob {

    private Long documentId;

    private Long requestId;

    private Integer documentOrder;

    private File pdfFile;

    public DocumentBlob() {
    }

    public DocumentBlob(Long documentId,
                        Long requestId,
                        Integer documentOrder,
                        File pdfFile) {

        this.documentId = documentId;
        this.requestId = requestId;
        this.documentOrder = documentOrder;
        this.pdfFile = pdfFile;
    }

    // getters/setters
}