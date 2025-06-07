package com.microservice_Cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@enableDiscoveryclient
@SpringBootApplication
public class MicroserviceClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceClienteApplication.class, args);
	}

}
