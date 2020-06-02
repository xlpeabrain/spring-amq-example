package com.example.drone.config;

import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
@EnableJms
public class JMSConfig{

    @Value("${activemq.broker-url}")
    private String brokerUrl;

    private ActiveMQConnectionFactory connectionFactory;
    private CachingConnectionFactory cachingConnectionFactory;

    @SneakyThrows
    @Bean
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        if (null == connectionFactory) {
            connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL(brokerUrl);
        }

        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        if (null == cachingConnectionFactory) {
            cachingConnectionFactory = new CachingConnectionFactory(
                    senderActiveMQConnectionFactory());
        }
        return cachingConnectionFactory;
    }

    //Default listener container factory that listens on queues
    @Bean(name="jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory());
        //Setting the PubSubDomain boolean determines if it's a queue or topic
        factory.setPubSubDomain(false);

        return factory;
    }

    //Default listener container factory that listens on topics
    @Bean(name="jmsTListenerContainerFactory")
    public DefaultJmsListenerContainerFactory jmsTListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory());
        factory.setPubSubDomain(true);

        return factory;
    }
}
