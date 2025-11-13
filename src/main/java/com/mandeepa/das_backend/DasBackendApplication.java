package com.mandeepa.das_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class DasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasBackendApplication.class, args);
	}

}
