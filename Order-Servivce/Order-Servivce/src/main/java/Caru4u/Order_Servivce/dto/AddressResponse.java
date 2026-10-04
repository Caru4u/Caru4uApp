package Caru4u.Order_Servivce.dto;

import lombok.Data;

@Data
public class AddressResponse {

    private Long customerId;

    private Long apartmentOrVillaId;

    private String apartmentOrVillaName;

    private String blockOrCrossName;

    private String plotNumber;
}