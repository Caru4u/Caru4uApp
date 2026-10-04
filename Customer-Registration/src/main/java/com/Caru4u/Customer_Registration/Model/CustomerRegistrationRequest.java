package com.Caru4u.Customer_Registration.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegistrationRequest {

    private String firstname;

    private String lastname;

    private String mobileNumber;

    private String password;

    private String mailid;


    // Customer selects this
    private Long apartmentOrVillaId;


    private String blockOrCrossName;

    private String plotNumber;
}