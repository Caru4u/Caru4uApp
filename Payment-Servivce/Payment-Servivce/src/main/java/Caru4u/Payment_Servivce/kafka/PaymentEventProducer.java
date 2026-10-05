package Caru4u.Payment_Servivce.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void paymentSuccess(
            Long orderId,
            String razorpayPaymentId) {

        String event =
                """
                {
                    "event":"PAYMENT_SUCCESS",
                    "orderId":%d,
                    "paymentId":"%s"
                }
                """.formatted(
                        orderId,
                        razorpayPaymentId
                );

        kafkaTemplate.send(
                "payment-events",
                orderId.toString(),
                event
        );
    }
}