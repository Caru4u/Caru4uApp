package com.Caru4u.Customer_Registration.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message;

    private String token;

    private Long customerId;

    private String firstname;

    private String lastname;

    private String mailid;

    private String mobileNumber;
}