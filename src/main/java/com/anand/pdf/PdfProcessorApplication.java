package com.anand.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PdfProcessorApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                PdfProcessorApplication.class,
                args);
    }
}