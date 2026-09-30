package com.caru4u.Caru4u_Cart_Service.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentCustomer {

    public Long getCustomerId() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new IllegalStateException(
                    "Customer is not authenticated"
            );
        }

        try {

            return Long.valueOf(
                    authentication.getName()
            );

        } catch (NumberFormatException e) {

            throw new IllegalStateException(
                    "Invalid customerId in authentication: "
                            + authentication.getName()
            );
        }
    }
}