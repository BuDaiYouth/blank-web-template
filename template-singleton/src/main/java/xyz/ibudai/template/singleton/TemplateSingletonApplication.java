package xyz.ibudai.template.singleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "xyz.ibudai")
public class TemplateSingletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateSingletonApplication.class, args);
    }
}
