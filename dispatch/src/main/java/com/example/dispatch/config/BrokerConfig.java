package com.example.dispatch.config;

import brave.sampler.Sampler;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

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

    @Bean
    public ConnectionFactory senderConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return activeMQConnectionFactory;
    }

    @Bean
    public ConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                senderConnectionFactory());
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

    @Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
