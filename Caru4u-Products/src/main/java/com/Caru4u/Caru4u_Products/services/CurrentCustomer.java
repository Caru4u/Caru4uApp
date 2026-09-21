package com.Caru4u.Caru4u_Products.services;

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

        String customerId =
                authentication.getName();

        try {

            return Long.valueOf(customerId);

        } catch (NumberFormatException e) {

            throw new IllegalStateException(
                    "Authenticated principal does not contain " +
                            "a valid customerId: " + customerId
            );
        }
    }
}