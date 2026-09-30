package com.caru4u.Caru4u_Cart_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Caru4uCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Caru4uCartServiceApplication.class, args);
	}

}
