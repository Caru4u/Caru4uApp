package com.Caru4u.Customer_Registration.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long customerId;

    private Long apartmentOrVillaId;

    private String apartmentOrVillaName;

    private String blockOrCrossName;

    private String plotNumber;
}