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
    // GET CHECKOUT DETAILS
    // =========================================================
    //
    // GET:
    // http://localhost:8085/api/checkout
    //
    // Header:
    // Authorization: Bearer <JWT>
    //
    // customerId is NOT passed from frontend.
    // It comes from the authenticated JWT.
    // =========================================================

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutResponse> getCheckout(

            Authentication authentication,

            @RequestHeader("Authorization")
            String authorization

    ) {

        // ==========================================
        // Check authentication
        // ==========================================

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        // ==========================================
        // Get logged-in customer from JWT
        // ==========================================

        Object principalObject =
                authentication.getPrincipal();


        if (!(principalObject instanceof CustomerPrincipal)) {

            throw new SecurityException(
                    "Invalid authenticated customer"
            );
        }


        CustomerPrincipal principal =
                (CustomerPrincipal) principalObject;


        Long customerId =
                principal.getCustomerId();


        if (customerId == null) {

            throw new SecurityException(
                    "Customer ID not found in authentication token"
            );
        }


        // ==========================================
        // Get checkout
        // ==========================================

        CheckoutResponse response =
                checkoutService.getCheckout(
                        customerId,
                        authorization
                );


        return ResponseEntity.ok(
                response
        );
    }


    // =========================================================
    // PLACE ORDER
    // =========================================================
    //
    // POST:
    // http://localhost:8085/api/checkout/place-order
    //
    // Header:
    // Authorization: Bearer <JWT>
    //
    // Body example:
    //
    // {
    //   "addressId": 1,
    //   "preferredDate": "2026-10-05",
    //   "preferredTime": "10:00 AM - 12:00 PM",
    //   "paymentMethod": "CASH_ON_SERVICE"
    // }
    //
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

        // ==========================================
        // Check authentication
        // ==========================================

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        // ==========================================
        // Get principal
        // ==========================================

        Object principalObject =
                authentication.getPrincipal();


        if (!(principalObject instanceof CustomerPrincipal)) {

            throw new SecurityException(
                    "Invalid authenticated customer"
            );
        }


        CustomerPrincipal principal =
                (CustomerPrincipal) principalObject;


        Long customerId =
                principal.getCustomerId();


        if (customerId == null) {

            throw new SecurityException(
                    "Customer ID not found in authentication token"
            );
        }


        // ==========================================
        // Place order
        // ==========================================

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
}