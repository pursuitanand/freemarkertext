# freemarkertext

A Maven/Spring Boot (Java 17) project containing two independent PDF-generation proofs of concept:

1. **Async PDF request pipeline** (`com.anand.pdf`) — a Spring Boot app that simulates picking up "PDF requests," merging their source documents, stamping a confidentiality footer, compressing the result with Ghostscript, and writing a final PDF per request.
2. **Freemarker + iText HTML-to-PDF demo** (`com.anand.freemarkertext`) — a standalone (non-Spring) demo that renders a Freemarker HTML template and converts it to a PDF using iText `html2pdf`.

These two apps are unrelated and do not share code paths.

## Requirements

- Java 17
- Maven (or use the bundled wrapper, `mvnw` / `mvnw.cmd`)
- [Ghostscript](https://www.ghostscript.com/) installed on Windows, since `CompressionService` shells out to a Ghostscript binary to compress merged PDFs. The path is currently hardcoded in `CompressionService` to:
  ```
  C:\Program Files\gs\gs10.07.1\bin\gswin64c.exe
  ```
  Update that path if your Ghostscript install differs.

## Building

```
mvnw.cmd clean install
```

## Running

### 1. PDF request pipeline (`com.anand.pdf`)

This is the project's `@SpringBootApplication` entry point:

```
mvnw.cmd spring-boot:run -Dspring-boot.run.main-class=com.anand.pdf.PdfProcessorApplication
```

On startup it loads two mock requests (`1001`, `1002`) from `MockRequestRepository`, processes each through merge → footer → compress, and writes results to `output/request-<id>.pdf`.

Mock source documents live under `src/main/resources/mock-data/request-<id>/`. To (re)generate large sample PDFs for request `1001`, run:

```
mvnw.cmd compile exec:java -Dexec.mainClass=com.anand.pdf.util.LargePdfGeneratorUtility
```

(or run `LargePdfGeneratorUtility.main()` directly from your IDE.) This produces 20 large, multi-page PDFs filled with random text, useful for exercising the pipeline's memory behavior under load.

If a run hangs, a Ghostscript process may be stuck. On Windows:

```
tasklist | findstr gs
taskkill /PID <pid> /F
```

### 2. Freemarker + iText demo (`com.anand.freemarkertext`)

Run `PdfGenerator.main()` directly (e.g. from your IDE) to render `src/main/resources/templates/template.ftlh` with a sample data model and convert it to `output.pdf` in the project root.

## Project structure

```
src/main/java/com/anand/pdf/            Async PDF request pipeline (Spring Boot)
  config/        Executor bean configuration
  create/        PDF-generation helpers used to build mock test data
  model/         RequestJob, DocumentBlob
  poller/        Loads pending requests into the in-memory queue on startup
  queue/         In-memory BlockingQueue of pending RequestJobs
  repository/    Mock request repository (stand-in for a DB-backed one)
  service/       Blob storage, merge, footer, and compression services
  util/          Memory logging and large-PDF generation utilities
  worker/        Worker threads that drain the queue and process jobs

src/main/java/com/anand/freemarkertext/ Standalone Freemarker + iText HTML-to-PDF demo
src/main/resources/templates/           Freemarker templates (.ftlh)
src/main/resources/mock-data/           Mock source PDFs per request id
```

See `CLAUDE.md` for a more detailed architecture walkthrough.
