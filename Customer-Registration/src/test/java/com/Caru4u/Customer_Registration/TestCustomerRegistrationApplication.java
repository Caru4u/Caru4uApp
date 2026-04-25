package com.Caru4u.Customer_Registration;

import org.springframework.boot.SpringApplication;

public class TestCustomerRegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.from(CustomerRegistrationApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
