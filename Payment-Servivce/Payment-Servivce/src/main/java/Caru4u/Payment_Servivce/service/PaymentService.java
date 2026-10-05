package Caru4u.Payment_Servivce.service;

import Caru4u.Payment_Servivce.dto.CreatePaymentRequest;
import Caru4u.Payment_Servivce.dto.CreatePaymentResponse;
import Caru4u.Payment_Servivce.dto.VerifyPaymentRequest;

public interface PaymentService {
    public CreatePaymentResponse createPayment(
            CreatePaymentRequest request)throws Exception;;

    public String verifyPayment(
            VerifyPaymentRequest request) throws Exception;;
}
