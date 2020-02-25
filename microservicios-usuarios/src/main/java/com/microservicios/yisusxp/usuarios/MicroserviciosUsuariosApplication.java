package com.microservicios.yisusxp.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EntityScan({"com.microservicios.yisusxp.commons.model"})
@EnableFeignClients
public class MicroserviciosUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosUsuariosApplication.class, args);
	}

}
