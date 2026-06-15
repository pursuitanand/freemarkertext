package com.anand.pdf.queue;

import com.anand.pdf.model.RequestJob;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class RequestQueue {

    private final BlockingQueue<RequestJob> queue =
            new LinkedBlockingQueue<>();

    public void submit(RequestJob job)
            throws InterruptedException {

        queue.put(job);
    }

    public RequestJob take()
            throws InterruptedException {

        return queue.take();
    }
}