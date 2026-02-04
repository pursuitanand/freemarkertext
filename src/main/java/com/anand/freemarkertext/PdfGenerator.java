package com.anand.freemarkertext;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PdfGenerator {
    public static void main(String[] args) {
        try {
            // Step 1: Load the Freemarker template
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates")); // Adjust path as needed
            cfg.setDefaultEncoding("UTF-8");

            // Step 2: Prepare the data model (Dynamic values)
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("gender", "Male");// Set gender dynamically
            dataModel.put("subscribe", false);

            // Step 3: Process the template and generate HTML string
            Template template = cfg.getTemplate("template.ftlh");
            StringWriter stringWriter = new StringWriter();
            template.process(dataModel, stringWriter);
            String htmlContent = stringWriter.toString();

            // Step 4: Convert HTML to PDF
            String outputPdfPath = "output.pdf";
            convertHtmlToPdf(htmlContent, outputPdfPath);

            System.out.println("PDF generated successfully at: " + outputPdfPath);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void convertHtmlToPdf(String html, String outputPdfPath) {
        try (OutputStream outputStream = new FileOutputStream(outputPdfPath)) {
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(html, outputStream, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
