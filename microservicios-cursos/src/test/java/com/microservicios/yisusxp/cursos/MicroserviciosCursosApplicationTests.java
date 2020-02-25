package com.microservicios.yisusxp.cursos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroserviciosCursosApplicationTests {

	@Test
	void contextLoads() {
		int numero = 10;
		for (int i = 2; i<=numero; i++) {
			if(esPrimo(i)) {
				System.out.println("numero primo: " + i);
			}
		}
	}

	boolean esPrimo(int n) {
		for(int i=2;i<n;i++) {
			if(n%i==0)
				return false;
		}
		return true;
	}
}
