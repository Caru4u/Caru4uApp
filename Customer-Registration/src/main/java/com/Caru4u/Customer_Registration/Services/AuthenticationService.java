package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Model.CustomerRegistor;
import com.Caru4u.Customer_Registration.Model.LoginRequest;
import com.Caru4u.Customer_Registration.Model.LoginResponse;
import com.Caru4u.Customer_Registration.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRegistorRepository customerService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    /**
     * Customer login using:
     *
     * Email + Password
     * OR
     * Mobile + Password
     */
    public LoginResponse login(
            LoginRequest request) {

        // ==========================================
        // 1. Validate request
        // ==========================================

        if (request == null) {

            throw new IllegalArgumentException(
                    "Login request cannot be null"
            );
        }

        String identifier =
                request.getIdentifier();

        String password =
                request.getPassword();


        if (identifier == null ||
                identifier.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Email or mobile number is required"
            );
        }


        if (password == null ||
                password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Password is required"
            );
        }


        identifier =
                identifier.trim();


        // ==========================================
        // 2. Find customer
        // ==========================================

        CustomerRegistor customer =
                findCustomer(identifier);


        // ==========================================
        // 3. Verify password
        // ==========================================

        boolean passwordMatches =
                passwordEncoder.matches(
                        password,
                        customer.getPassword()
                );


        if (!passwordMatches) {

            throw new IllegalArgumentException(
                    "Invalid password"
            );
        }


        // ==========================================
        // 4. Generate JWT
        // ==========================================

        String token =
                jwtService.generateToken(

                        customer.getCustomerId(),

                        customer.getMailid()
                );


        // ==========================================
        // 5. Return login response
        // ==========================================

        return LoginResponse.builder()

                .message(
                        "Login successful!"
                )

                .token(token)

                .customerId(
                        customer.getCustomerId()
                )

                .firstname(
                        customer.getFirstname()
                )

                .lastname(
                        customer.getLastname()
                )

                .mailid(
                        customer.getMailid()
                )

                .mobileNumber(
                        customer.getMobileNumber()
                )

                .build();
    }


    /**
     * Search customer using email/mobile.
     */
    private CustomerRegistor findCustomer(
            String identifier) {


        // ==========================================
        // Email login
        // ==========================================

        if (identifier.contains("@")) {

            return customerService
                    .findByMailidIgnoreCase(
                            identifier
                    )
                    .orElseThrow(
                            () ->
                                    new IllegalArgumentException(
                                            "Email address is not registered"
                                    )
                    );
        }


        // ==========================================
        // Mobile login
        // ==========================================

        return customerService
                .findByMobileNumber(
                        identifier
                )
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Mobile number is not registered"
                                )
                );
    }
}