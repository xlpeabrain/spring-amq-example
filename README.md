# spring-amq-example
A sample with projects for embedded AMQ broker and seperate client

Dispatch is a Spring Boot app that will start with an embedded ActiveMQ broker at tcp://localhost:61616
The properties can be configured in the application.properties file

Drone is a simple Spring Boot app with a jms client set up to listen to a queue and a topic.
Drone will start on a new port each time, and multiple instances can run on the same development machine.