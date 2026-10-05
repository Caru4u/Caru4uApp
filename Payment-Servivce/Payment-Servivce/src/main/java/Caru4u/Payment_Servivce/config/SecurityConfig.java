package Caru4u.Payment_Servivce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .authorizeHttpRequests(auth -> auth

                        // Temporary for Postman testing
                        .requestMatchers(
                                "/api/payments/create",
                                "/api/payments/verify"
                        )
                        .permitAll()

                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}