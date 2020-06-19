package com.example.dispatch.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.dispatch.jms.ActiveMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class ActivityController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected final ActiveMQSender sender;
    private RestTemplate restTemplate;
    @Value("${persistent.store.url}")
    private String persistentStoreURL;
    @Value("${topic.job}")
    private String newJobsTopic;

    @Autowired
    public ActivityController(ActiveMQSender sender, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.sender = sender;
    }

    @GetMapping(path = "/q")
    public void submitQ() {
        sender.sendQ();
        logger.info("Sending Message into Q.");
    }

    @GetMapping(path ="/t")
    public void submitTopic() {
        sender.sendT();
        logger.info("Sending Message into Topic");

    }

    @GetMapping(path= "/job")
    public void createNewJob() throws URISyntaxException {
        //Submit job to warehouse
        logger.info("Generating new job");
        String jobId = restTemplate.getForObject(new URI(persistentStoreURL+"job"), String.class);
        logger.info("New Job Id: {}", jobId);
        //Send job ID to Topic
        sender.sendToTopic(true, newJobsTopic,jobId);
        logger.info("New Job published to topic");

    }

}
