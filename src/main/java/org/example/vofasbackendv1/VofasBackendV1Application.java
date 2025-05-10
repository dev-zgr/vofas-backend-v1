package org.example.vofasbackendv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class VofasBackendV1Application {

    public static void main(String[] args) {
        SpringApplication.run(VofasBackendV1Application.class, args);
    }

}