package com.Caru4u.Caru4u_Products.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackagePriceUpdateRequest {
    private BigDecimal price;
}
