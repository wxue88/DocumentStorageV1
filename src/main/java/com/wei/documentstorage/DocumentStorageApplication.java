package com.wei.documentstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.wei.documentstorage.config.DocumentStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({DocumentStorageProperties.class})
public class DocumentStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentStorageApplication.class, args);
	}

}
