package com.example.dispatch.config;

import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class BrokerConfig {

    @Value("${activemq.broker-url}")
    private String brokerUrl;


    @Bean
    public BrokerService broker() throws Exception {

        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(true);
        broker.addConnector(brokerUrl);
        return broker;
    }

    @SneakyThrows
    @Bean
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                senderActiveMQConnectionFactory());
    }

    @Bean(name="QTemplate")
    public JmsTemplate QTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }

    @Bean(name="TTemplate")
    public JmsTemplate TTemplate() {
        JmsTemplate template = new JmsTemplate(cachingConnectionFactory());
        template.setPubSubDomain(true);
        return template;
    }

}
