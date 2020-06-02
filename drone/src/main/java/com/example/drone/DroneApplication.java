package com.example.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneApplication.class, args);
	}

}
