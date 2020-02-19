package com.microservicios.yisusxp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroserviciosEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosEurekaServerApplication.class, args);
	}

}
