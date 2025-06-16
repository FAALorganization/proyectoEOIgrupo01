package com.grupo01.java6.faal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
//@EnableGlobalMethodSecurity(prePostEnabled = true) //Si queremos usar PreAuthorize
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
