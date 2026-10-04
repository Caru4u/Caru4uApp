package com.Caru4u.Customer_Registration.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerPrincipal {

    private Long customerId;
    private String email;
}