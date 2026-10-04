package Caru4u.Order_Servivce.dto;

import Caru4u.Order_Servivce.entity.PaymentMethod;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlaceOrderRequest {

    private LocalDate preferredDate;

    private String preferredTime;

    private PaymentMethod paymentMethod;
}