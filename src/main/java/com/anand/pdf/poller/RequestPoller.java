package com.anand.pdf.poller;

import com.anand.pdf.model.RequestJob;
import com.anand.pdf.queue.RequestQueue;
import com.anand.pdf.repository.MockRequestRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestPoller {

    private final MockRequestRepository repository;
    private final RequestQueue queue;

    @PostConstruct
    public void load()
            throws Exception {

        for(RequestJob job :
                repository.findNewRequests()) {

            queue.submit(job);
        }
    }
}