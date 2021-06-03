package com.SGW.Phrases

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.web.bind.annotation.CrossOrigin

import javax.crypto.SecretKey


@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
class PhrasesApplication {


	static void main(String[] args) {
		SpringApplication.run(PhrasesApplication.class, args)
	}


}
