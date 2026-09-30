package com.caru4u.Caru4u_Cart_Service.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CartIteamResponse {

    private Long cartItemId;

    private Long productId;

    private Long packageId;

    private String productName;

    private String  packageName;

    private String vehicleType;

    private String frequency;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}
