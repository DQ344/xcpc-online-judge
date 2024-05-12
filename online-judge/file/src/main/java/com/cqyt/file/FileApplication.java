package com.cqyt.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FileApplication.class);
        app.run(args);
    }
}
