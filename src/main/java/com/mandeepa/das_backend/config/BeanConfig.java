package com.mandeepa.das_backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class BeanConfig {
    @Bean
    public ModelMapper modelMapper() {  // This is a bean that used to map objects to other objects
        return new ModelMapper();
    }
}
