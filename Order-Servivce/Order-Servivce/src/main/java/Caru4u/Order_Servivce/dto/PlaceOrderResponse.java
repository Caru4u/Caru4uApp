package Caru4u.Order_Servivce.dto;

import Caru4u.Order_Servivce.entity.OrderStatus;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PlaceOrderResponse {

    private Long orderId;

    private OrderStatus status;

    private BigDecimal total;

    private String message;
}