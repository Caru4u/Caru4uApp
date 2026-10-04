package Caru4u.Order_Servivce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {

    private Long cartId;

    private Long customerId;

    private BigDecimal discount;

    private List<CartItemResponse> items;
}