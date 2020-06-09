package com.example.warehouse.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@ToString
public class ClaimRequest {

    @Getter @Setter
    private String jobId;
    @Getter @Setter
    private String droneId;

    protected ClaimRequest(){};

    @Autowired
    public ClaimRequest (String jobId, String droneId) {
        this.jobId = jobId;
        this.droneId = droneId;
    }


}
