package Caru4u.Order_Servivce.controller;

import Caru4u.Order_Servivce.dto.CheckoutResponse;
import Caru4u.Order_Servivce.dto.PlaceOrderRequest;
import Caru4u.Order_Servivce.dto.PlaceOrderResponse;
import Caru4u.Order_Servivce.security.CustomerPrincipal;
import Caru4u.Order_Servivce.services.CheckoutService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutController {

    private final CheckoutService checkoutService;


    // =========================================================
    // GET CHECKOUT
    // =========================================================

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutResponse> getCheckout(

            Authentication authentication,

            @RequestHeader("Authorization")
            String authorization

    ) {

        Long customerId =
                getAuthenticatedCustomerId(
                        authentication
                );


        CheckoutResponse response =
                checkoutService.getCheckout(
                        customerId,
                        authorization
                );


        return ResponseEntity.ok(response);
    }


    // =========================================================
    // PLACE ORDER
    // =========================================================

    @PostMapping("/place-order")
    public ResponseEntity<PlaceOrderResponse> placeOrder(

            Authentication authentication,

            @RequestHeader("Authorization")
            String authorization,

            @Valid
            @RequestBody
            PlaceOrderRequest request

    ) {

        Long customerId =
                getAuthenticatedCustomerId(
                        authentication
                );


        System.out.println(
                "=============================="
        );

        System.out.println(
                "PLACE ORDER"
        );

        System.out.println(
                "Customer ID: "
                        + customerId
        );

        System.out.println(
                "Address ID: "
                        + request.getAddressId()
        );

        System.out.println(
                "Payment Method: "
                        + request.getPaymentMethod()
        );

        System.out.println(
                "Preferred Date: "
                        + request.getPreferredDate()
        );

        System.out.println(
                "Preferred Time: "
                        + request.getPreferredTime()
        );

        System.out.println(
                "=============================="
        );


        PlaceOrderResponse response =
                checkoutService.placeOrder(
                        customerId,
                        authorization,
                        request
                );


        return ResponseEntity.ok(
                response
        );
    }


    // =========================================================
    // AUTHENTICATED CUSTOMER
    // =========================================================

    private Long getAuthenticatedCustomerId(
            Authentication authentication
    ) {

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        Object principalObject =
                authentication.getPrincipal();


        if (!(principalObject
                instanceof CustomerPrincipal)) {

            throw new SecurityException(
                    "Invalid authenticated customer"
            );
        }


        CustomerPrincipal principal =
                (CustomerPrincipal)
                        principalObject;


        Long customerId =
                principal.getCustomerId();


        if (customerId == null) {

            throw new SecurityException(
                    "Customer ID not found in authentication token"
            );
        }


        return customerId;
    }
}