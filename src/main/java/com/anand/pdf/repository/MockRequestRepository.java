package com.anand.pdf.repository;

import com.anand.pdf.model.RequestJob;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MockRequestRepository {

    public List<RequestJob> findNewRequests() {

        return Arrays.asList(
                new RequestJob(1001L),
                new RequestJob(1002L)
        );
    }
}

// Later Replace with  SELECT REQUEST_ID
//FROM PDF_REQUEST
//WHERE STATUS='NEW'