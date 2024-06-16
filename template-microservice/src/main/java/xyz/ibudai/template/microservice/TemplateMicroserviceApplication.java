package xyz.ibudai.template.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "xyz.ibudai")
public class TemplateMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateMicroserviceApplication.class, args);
    }
}
