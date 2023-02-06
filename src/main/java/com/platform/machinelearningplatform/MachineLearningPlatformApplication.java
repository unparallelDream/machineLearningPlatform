package com.platform.machinelearningplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MachineLearningPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(MachineLearningPlatformApplication.class, args);
    }

}
