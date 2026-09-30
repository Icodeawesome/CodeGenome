package com.codegenome.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeGenomeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                CodeGenomeBackendApplication.class,
                args
        );
    }
}
