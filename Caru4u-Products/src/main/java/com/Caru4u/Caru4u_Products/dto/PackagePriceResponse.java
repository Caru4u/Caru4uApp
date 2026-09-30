package com.Caru4u.Caru4u_Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackagePriceResponse {

    private Long id;

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

    // Add these
    private LocalDate validFrom;
    private LocalDate validTo;
}