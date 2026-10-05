package Caru4u.Order_Servivce.dto;

import Caru4u.Order_Servivce.entity.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaceOrderRequest {
    @NotNull
    private Long addressId;

    private LocalDate preferredDate;

    private String preferredTime;

    private PaymentMethod paymentMethod;
}