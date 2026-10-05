package Caru4u.Payment_Servivce.controller;



import Caru4u.Payment_Servivce.dto.CreatePaymentRequest;
import Caru4u.Payment_Servivce.dto.CreatePaymentResponse;
import Caru4u.Payment_Servivce.dto.VerifyPaymentRequest;
import Caru4u.Payment_Servivce.service.PaymentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/create")
    public ResponseEntity<CreatePaymentResponse> createPayment(
            @RequestBody CreatePaymentRequest request)
            throws Exception {

        return ResponseEntity.ok(
                paymentService.createPayment(request)
        );
    }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestBody VerifyPaymentRequest request)
            throws Exception {

        return ResponseEntity.ok(
                paymentService.verifyPayment(request)
        );
    }
}