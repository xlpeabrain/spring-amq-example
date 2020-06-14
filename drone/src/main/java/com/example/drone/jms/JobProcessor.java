package com.example.drone.jms;

import com.example.drone.request.ClaimRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
public class JobProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private UUID cloneId;
    private RestTemplate restTemplate;
    @Value("${persistent.store.url}")
    private String persistentStoreUrl;

    protected JobProcessor() {};

    @Autowired
    public JobProcessor(UUID cloneId) {
        this.cloneId = cloneId;
        restTemplate = new RestTemplate();
    }

    @JmsListener(destination = "jobs", containerFactory = "jmsTListenerContainerFactory")
    public void processJob(String message) {
        logger.info("Received Job message: {}", message);
        //Try to claim job
        ClaimRequest request = new ClaimRequest(message, cloneId.toString());
        ResponseEntity<String> response = restTemplate.postForEntity(URI.create(persistentStoreUrl + "jobClaim")
                                                                    , request
                                                                    , String.class);
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            logger.info("Job {} assigned to drone {}", message, cloneId.toString());
        } else if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            logger.info("Claim Request unsuccessful for job {}, drone {}", message, cloneId.toString());
        }
    }
}
