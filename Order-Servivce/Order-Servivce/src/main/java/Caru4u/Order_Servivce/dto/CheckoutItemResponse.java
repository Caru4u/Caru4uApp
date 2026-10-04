package Caru4u.Order_Servivce.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutItemResponse {

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