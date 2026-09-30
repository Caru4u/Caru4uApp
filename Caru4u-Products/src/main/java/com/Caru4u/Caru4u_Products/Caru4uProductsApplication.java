package com.Caru4u.Caru4u_Products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Caru4uProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Caru4uProductsApplication.class, args);
	}

}
