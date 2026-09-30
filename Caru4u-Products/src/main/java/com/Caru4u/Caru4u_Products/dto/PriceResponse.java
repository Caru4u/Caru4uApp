package com.Caru4u.Caru4u_Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceResponse implements Serializable {

    private Long frequencyId;

    private Long vehicleTypeId;

    private String frequency;

    private String description;

    private BigDecimal price;
}