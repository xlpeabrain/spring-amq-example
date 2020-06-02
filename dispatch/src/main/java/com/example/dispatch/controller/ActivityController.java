package com.example.dispatch.controller;

import com.example.dispatch.jms.ActiveMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    private Logger logger = LoggerFactory.getLogger(ActivityController.class.getName());

    protected final ActiveMQSender sender;

    @Autowired
    public ActivityController(ActiveMQSender sender) {
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

}
