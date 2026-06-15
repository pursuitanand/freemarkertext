package com.anand.pdf.worker;


import com.anand.pdf.model.RequestJob;
import com.anand.pdf.queue.RequestQueue;
import com.anand.pdf.service.RequestProcessor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfWorker {

    private final RequestQueue queue;
    private final RequestProcessor processor;

    @PostConstruct
    public void start() {

        for(int i=0;i<4;i++) {

            new Thread(() -> {

                while(true) {
                    RequestJob job = null;
                    try {

                        job = queue.take();

                        processor.process(job);

                    }
                    catch (OutOfMemoryError oom) {

                        System.out.println("OOM processing request "+ job.getRequestId()+ "::"+
                                oom);

                        //markRequestFailed(job);

                        System.gc();
                    }
                    catch(Exception ex) {

                        ex.printStackTrace();
                    }
                }

            }).start();
        }
    }
}