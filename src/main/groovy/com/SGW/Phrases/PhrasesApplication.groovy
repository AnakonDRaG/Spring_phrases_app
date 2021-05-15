package com.SGW.Phrases

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties


import javax.crypto.SecretKey


@SpringBootApplication
class PhrasesApplication {


	static void main(String[] args) {
		//SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		//String secretString = Encoders.BASE64.encode(secretKey.getEncoded());

		SpringApplication.run(PhrasesApplication.class, args)
	}


}
