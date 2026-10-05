package Caru4u.Payment_Servivce.service;



import Caru4u.Payment_Servivce.dto.CreatePaymentRequest;
import Caru4u.Payment_Servivce.dto.CreatePaymentResponse;
import Caru4u.Payment_Servivce.dto.VerifyPaymentRequest;
import Caru4u.Payment_Servivce.entity.Payment;
import Caru4u.Payment_Servivce.kafka.PaymentEventProducer;
import Caru4u.Payment_Servivce.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import lombok.RequiredArgsConstructor;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;


    @Transactional
    public CreatePaymentResponse createPayment(
            CreatePaymentRequest request) throws Exception {

        /*
         * Razorpay requires amount in paise.
         *
         * ₹100 -> 10000
         */

        long amountInPaise =
                request.getAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .longValueExact();


        JSONObject orderRequest = new JSONObject();

        orderRequest.put(
                "amount",
                amountInPaise
        );

        orderRequest.put(
                "currency",
                "INR"
        );

        orderRequest.put(
                "receipt",
                "CARU4U_" + request.getOrderId()
        );


        Order razorpayOrder =
                razorpayClient
                        .orders
                        .create(orderRequest);


        String razorpayOrderId =
                razorpayOrder.get("id");


        Payment payment =
                Payment.builder()

                        .customerId(
                                request.getCustomerId()
                        )

                        .orderId(
                                request.getOrderId()
                        )

                        .amount(
                                request.getAmount()
                        )

                        .currency("INR")

                        .razorpayOrderId(
                                razorpayOrderId
                        )

                        .status("CREATED")

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .updatedAt(
                                LocalDateTime.now()
                        )

                        .build();


        payment =
                paymentRepository.save(payment);


        return CreatePaymentResponse.builder()

                .paymentId(
                        payment.getId()
                )

                .razorpayOrderId(
                        razorpayOrderId
                )

                .keyId(
                        keyId
                )

                .amount(
                        amountInPaise
                )

                .currency("INR")

                .status("CREATED")

                .build();
    }


    @Transactional
    public String verifyPayment(
            VerifyPaymentRequest request) throws Exception {


        Payment payment =
                paymentRepository
                        .findByRazorpayOrderId(
                                request.getRazorpayOrderId()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Payment not found"
                                )
                        );


        JSONObject attributes =
                new JSONObject();

        attributes.put(
                "razorpay_order_id",
                request.getRazorpayOrderId()
        );

        attributes.put(
                "razorpay_payment_id",
                request.getRazorpayPaymentId()
        );

        attributes.put(
                "razorpay_signature",
                request.getRazorpaySignature()
        );


        boolean valid =
                Utils.verifyPaymentSignature(
                        attributes,
                        keySecret
                );


        if (!valid) {

            payment.setStatus(
                    "VERIFICATION_FAILED"
            );

            payment.setUpdatedAt(
                    LocalDateTime.now()
            );

            paymentRepository.save(payment);

            throw new RuntimeException(
                    "Invalid Razorpay signature"
            );
        }


        payment.setRazorpayPaymentId(
                request.getRazorpayPaymentId()
        );

        payment.setStatus(
                "PAID"
        );

        payment.setUpdatedAt(
                LocalDateTime.now()
        );

        paymentRepository.save(payment);


        paymentEventProducer.paymentSuccess(
                payment.getOrderId(),
                request.getRazorpayPaymentId()
        );


        return "Payment verified successfully";
    }
}