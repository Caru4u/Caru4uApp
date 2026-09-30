package com.caru4u.Caru4u_Cart_Service.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
public class CartResponse {

    private Long cartId;

    private Long customerId;

    private Integer totalItems;

    private List<CartIteamResponse> items;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal total;

}
