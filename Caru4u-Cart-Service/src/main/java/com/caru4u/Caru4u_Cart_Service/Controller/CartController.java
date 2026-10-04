package com.caru4u.Caru4u_Cart_Service.Controller;

import com.caru4u.Caru4u_Cart_Service.model.AddToCartRequest;
import com.caru4u.Caru4u_Cart_Service.model.CartResponse;
import com.caru4u.Caru4u_Cart_Service.model.UpdateCartIteamRequest;

import com.caru4u.Caru4u_Cart_Service.Security.CustomerPrincipal;

import com.caru4u.Caru4u_Cart_Service.service.CartServices;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartServices cartService;


    // =========================================================
    // 1. GET LOGGED-IN CUSTOMER CART
    // =========================================================
    //
    // GET http://localhost:8086/api/cart
    //
    // Authorization:
    // Bearer <JWT>
    //
    // customerId comes from JWT.
    // =========================================================

    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication
    ) {

        CustomerPrincipal principal =
                getPrincipal(authentication);

        Long customerId =
                principal.getCustomerId();

        CartResponse response =
                cartService.getCart(customerId);

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 2. ADD ITEM TO CART
    // =========================================================
    //
    // POST http://localhost:8086/api/cart/items
    //
    // IMPORTANT:
    //
    // Your service method is:
    //
    // addToCart(Long customerId, AddToCartRequest request)
    //
    // NOT:
    //
    // addItem(...)
    //
    // =========================================================

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(

            Authentication authentication,

            @Valid
            @RequestBody
            AddToCartRequest request

    ) {

        CustomerPrincipal principal =
                getPrincipal(authentication);

        Long customerId =
                principal.getCustomerId();

        CartResponse response =
                cartService.addToCart(
                        customerId,
                        request
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 3. UPDATE CART ITEM QUANTITY
    // =========================================================
    //
    // PUT
    // /api/cart/items/{cartItemId}
    //
    // =========================================================

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateQuantity(

            Authentication authentication,

            @PathVariable
            Long cartItemId,

            @Valid
            @RequestBody
            UpdateCartIteamRequest request

    ) {

        CustomerPrincipal principal =
                getPrincipal(authentication);

        Long customerId =
                principal.getCustomerId();

        CartResponse response =
                cartService.updateQuantity(
                        customerId,
                        cartItemId,
                        request
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 4. REMOVE ITEM FROM CART
    // =========================================================
    //
    // DELETE
    // /api/cart/items/{cartItemId}
    //
    // =========================================================

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeItem(

            Authentication authentication,

            @PathVariable
            Long cartItemId

    ) {

        CustomerPrincipal principal =
                getPrincipal(authentication);

        Long customerId =
                principal.getCustomerId();

        CartResponse response =
                cartService.removeItem(
                        customerId,
                        cartItemId
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // 5. CLEAR CART
    // =========================================================
    //
    // DELETE
    // http://localhost:8086/api/cart
    //
    // This API is called by Checkout Service
    // after order placement.
    //
    // =========================================================

    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            Authentication authentication
    ) {

        CustomerPrincipal principal =
                getPrincipal(authentication);

        Long customerId =
                principal.getCustomerId();

        cartService.clearCart(customerId);

        return ResponseEntity
                .noContent()
                .build();
    }


    // =========================================================
    // COMMON METHOD
    // GET LOGGED-IN CUSTOMER FROM SPRING SECURITY
    // =========================================================

    private CustomerPrincipal getPrincipal(
            Authentication authentication
    ) {

        // -----------------------------------------
        // Authentication missing
        // -----------------------------------------

        if (authentication == null) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        // -----------------------------------------
        // Authentication failed
        // -----------------------------------------

        if (!authentication.isAuthenticated()) {

            throw new SecurityException(
                    "Customer is not authenticated"
            );
        }


        // -----------------------------------------
        // Get principal
        // -----------------------------------------

        Object principalObject =
                authentication.getPrincipal();


        // -----------------------------------------
        // Make sure our JWT filter created
        // CustomerPrincipal
        // -----------------------------------------

        if (!(principalObject
                instanceof CustomerPrincipal principal)) {

            throw new SecurityException(
                    "Invalid authenticated customer"
            );
        }


        // -----------------------------------------
        // customerId must exist
        // -----------------------------------------

        if (principal.getCustomerId() == null) {

            throw new SecurityException(
                    "Customer ID not found in JWT"
            );
        }


        return principal;
    }
}