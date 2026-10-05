package Caru4u.Payment_Servivce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse {

    private Long paymentId;

    private String razorpayOrderId;

    private String keyId;

    private Long amount;

    private String currency;

    private String status;
}