# spring-amq-example
A sample with projects for embedded AMQ broker and seperate client to simulate a job dispatch system.

Dispatch is a Spring Boot app that will start with an embedded ActiveMQ broker at tcp://localhost:61616
The properties can be configured in the application.properties file

Drone is a simple Spring Boot app with a jms client set up to listen to a queue and a topic.
Drone will start on a new port each time, and multiple instances can run on the same development machine.

Warehouse is a Spring Boot JPA app, that starts with an embedded H2 DB by default. Data is persisted for the lifetime of the application.
Warehouse is used to persist the jobs created. and updated with the droneID which claims the job.