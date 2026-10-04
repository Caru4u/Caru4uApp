package Caru4u.Order_Servivce.services;

import Caru4u.Order_Servivce.dto.CheckoutResponse;
import Caru4u.Order_Servivce.dto.PlaceOrderRequest;
import Caru4u.Order_Servivce.dto.PlaceOrderResponse;


public interface CheckoutService {

    CheckoutResponse getCheckout(
            Long customerId,
            String authorization
    );


    PlaceOrderResponse placeOrder(
            Long customerId,
            String authorization,
            PlaceOrderRequest request
    );
}