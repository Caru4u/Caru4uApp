package com.caru4u.Caru4u_Cart_Service.Controller;

import com.caru4u.Caru4u_Cart_Service.model.AddToCartRequest;
import com.caru4u.Caru4u_Cart_Service.model.CartResponse;
import com.caru4u.Caru4u_Cart_Service.model.UpdateCartIteamRequest;
import com.caru4u.Caru4u_Cart_Service.service.CartServices;
import com.caru4u.Caru4u_Cart_Service.service.CurrentCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServices cartService;

    private final CurrentCustomer currentCustomer;

    /*
     * Get logged-in customer's cart
     */
    @GetMapping
    public ResponseEntity<CartResponse> getCart() {

        Long customerId =
                currentCustomer.getCustomerId();

        return ResponseEntity.ok(
                cartService.getCart(customerId)
        );
    }

    /*
     * Add service to cart
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(@RequestParam Long customerId,

            @Valid
            @RequestBody
            AddToCartRequest request) {

//        Long customerId =
//                currentCustomer.getCustomerId();

        CartResponse response =
                cartService.addToCart(
                        customerId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    /*
     * Update quantity
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateQuantity(

            @PathVariable
            Long itemId,

            @Valid
            @RequestBody
            UpdateCartIteamRequest request) {

        Long customerId =
                currentCustomer.getCustomerId();

        return ResponseEntity.ok(
                cartService.updateQuantity(
                        customerId,
                        itemId,
                        request
                )
        );
    }

    /*
     * Remove one cart item
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable Long itemId) {

        Long customerId = currentCustomer.getCustomerId();

        return ResponseEntity.ok(
                cartService.removeItem(
                        customerId,
                        itemId
                )
        );
    }

    /*
     * Clear complete cart
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {

        Long customerId =
                currentCustomer.getCustomerId();

        cartService.clearCart(customerId);

        return ResponseEntity.noContent().build();
    }
}
