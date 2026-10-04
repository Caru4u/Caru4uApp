package Caru4u.Order_Servivce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long cartItemId;

    private Long productId;

    private Long packageId;

    private String productName;

    private String packageName;

    private String vehicleType;

    private String frequency;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;
}