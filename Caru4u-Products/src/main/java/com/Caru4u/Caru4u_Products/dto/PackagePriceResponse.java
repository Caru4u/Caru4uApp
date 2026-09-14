package com.Caru4u.Caru4u_Products.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackagePriceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long packageId;

    private Long vehicleTypeId;

    private Long frequencyId;

    private BigDecimal price;

    private String currency;

    private Boolean active;

    private LocalDate validFrom;

    private LocalDate validTo;
}