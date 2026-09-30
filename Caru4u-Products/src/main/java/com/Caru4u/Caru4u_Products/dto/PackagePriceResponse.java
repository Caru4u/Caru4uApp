package com.Caru4u.Caru4u_Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackagePriceResponse {
public class PackagePriceResponse implements Serializable {

    private Long productId;
    private String productName;
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long packageId;
    private String packageName;

    private Long vehicleTypeId;
    private String vehicleType;

    private Long frequencyId;
    private String frequency;

    private BigDecimal price;

    private String currency;

    private Boolean active;

    private LocalDate validFrom;

    private LocalDate validTo;
}