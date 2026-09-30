package com.caru4u.Caru4u_Cart_Service.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackagePriceResponse {

    private Long productId;
    private String productName;

    private Long packageId;
    private String packageName;

    private Long vehicleTypeId;
    private String vehicleType;

    private Long frequencyId;
    private String frequency;

    private BigDecimal price;
    private String currency;

    private Boolean active;

}
