package com.nibss.dot_nibss_simulation_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DotNibssSimulationTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(DotNibssSimulationTaskApplication.class, args);
    }

}
