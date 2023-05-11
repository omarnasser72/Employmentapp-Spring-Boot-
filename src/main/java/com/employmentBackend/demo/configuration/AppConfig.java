package com.employmentBackend.demo.configuration;

import com.employmentBackend.demo.middleware.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {

    @Bean
    public Authentication authentication() {
        return new Authentication();
    }

}