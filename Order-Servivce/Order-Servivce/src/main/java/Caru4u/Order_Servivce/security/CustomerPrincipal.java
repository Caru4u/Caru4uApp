package Caru4u.Order_Servivce.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerPrincipal {

    private Long customerId;
    private String email;
}