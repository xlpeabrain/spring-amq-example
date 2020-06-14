package com.example.drone.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private Logger logger = LoggerFactory.getLogger(MessageListener.class.getName());

    @JmsListener(destination = "${activemq.queue.name}", containerFactory = "jmsListenerContainerFactory")
    public void receiveQueueMsg(String message) {
        logger.info("Received Queue message: {}", message);
    }

    @JmsListener(destination = "${activemq.topic.name}", containerFactory = "jmsTListenerContainerFactory")
    public void receiveTopicMsg(String message) {
        logger.info("Received Topic message: {}", message);
    }


}
