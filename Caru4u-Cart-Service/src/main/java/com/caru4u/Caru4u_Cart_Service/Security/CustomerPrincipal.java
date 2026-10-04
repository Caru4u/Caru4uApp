package com.caru4u.Caru4u_Cart_Service.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerPrincipal {

    private Long customerId;

    private String email;
}