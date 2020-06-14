package com.example.dispatch.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQSender {

    private Logger logger = LoggerFactory.getLogger(ActiveMQSender.class.getName());

    private JmsTemplate qTemplate;
    private JmsTemplate tTemplate;

    @Autowired
    public ActiveMQSender(JmsTemplate QTemplate, JmsTemplate TTemplate)
    {
        this.qTemplate = QTemplate;
        this.tTemplate = TTemplate;
    }

    public void sendQ() {
        qTemplate.send("my-queue-1", s -> s.createTextMessage("Test Q Message"));
    }

    public void sendT() {
        tTemplate.send("my-topic-1", s -> s.createTextMessage("Test T Message"));
    }

    public void sendToTopic(boolean isTopic, String destination, String message) {
        if (isTopic) {
            tTemplate.send(destination, s -> s.createTextMessage(message));
        } else {
            qTemplate.send(destination, s -> s.createTextMessage(message));
        }
    }
}
