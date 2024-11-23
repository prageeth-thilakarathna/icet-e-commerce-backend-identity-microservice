package edu.icet.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ECommerceBackendIdentityMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBackendIdentityMicroserviceApplication.class, args);
    }
}