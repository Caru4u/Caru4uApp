package com.Caru4u.Customer_Registration.Config;



import com.Caru4u.Customer_Registration.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // REST API - disable CSRF
                .csrf(csrf ->
                        csrf.disable()
                )

                .cors(cors -> {
                })


                // JWT application should be stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                .authorizeHttpRequests(auth -> auth

                        // =====================================
                        // PUBLIC APIs
                        // =====================================
                        .requestMatchers(

                                // Registration
                                "/auth/Customer/register",

                                // Login
                                "/auth/Customer/login",

                                // Registration dropdown
                                "/auth/Customer/address"

                        )
                        .permitAll()


                        // =====================================
                        // LOGIN REQUIRED
                        // =====================================
                        .requestMatchers(
                                "/auth/Customer/me/**"
                        )
                        .authenticated()


                        // Everything else requires JWT
                        .anyRequest()
                        .authenticated()
                )


                // =====================================
                // JWT FILTER
                // =====================================
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}