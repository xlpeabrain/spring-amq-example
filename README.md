# spring-amq-example
A sample with projects for embedded AMQ broker and seperate client to simulate a job dispatch system.

Dispatch is a Spring Boot app that will start with an embedded ActiveMQ broker at tcp://localhost:61616
The properties can be configured in the application.properties file

Drone is a simple Spring Boot app with a jms client set up to listen to a queue and a topic.
Drone will start on a new port each time, and multiple instances can run on the same development machine.

Warehouse is a Spring Boot JPA app, that starts with an embedded H2 DB by default. Data is persisted for the lifetime of the application.
Warehouse is used to persist the jobs created. and updated with the droneID which claims the job.

**Process Flow**

![Process Flow](/images/jobDispatchFlow.jpg)

1. A user requests for a new job by calling http://localhost:8080/job
2. Dispatch makes a call to warehouse to create a job in persistent store at http://localhost:8090/job
A job ID is returned. 
3. Dispatch submits the job ID to the JMS topic "jobs"
4. Drones listening to the topic "jobs", receive the message. And try to claim the job by posting a request to Warehouse at http://localhost:8090/jobClaim
A successful drone will have the drone ID persisted in the persistent store, and get a OK response. Unsuccessful drones will receive a NO_CONTENT response