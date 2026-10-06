package com.energi.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    @Bean
    CommandLineRunner loadData(ComponentRepository componentRepository) {
        return args -> {
            if (componentRepository.count() == 0) {
                componentRepository.save(new Component("Hovedbatteri", Component.Status.ACTIVE, "Batteri"));
                componentRepository.save(new Component("Transformator T1", Component.Status.MAINTENANCE, "Transformator"));
                componentRepository.save(new Component("Hovedmeter", Component.Status.INACTIVE, "Meter"));
            }
        };
    }
}
