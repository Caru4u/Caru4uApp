package Caru4u.Payment_Servivce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
}