package com.caru4u.Caru4u_Cart_Service.Controller;

import com.caru4u.Caru4u_Cart_Service.model.AddToCartRequest;
import com.caru4u.Caru4u_Cart_Service.model.CartResponse;
import com.caru4u.Caru4u_Cart_Service.model.UpdateCartIteamRequest;
import com.caru4u.Caru4u_Cart_Service.service.CartServices;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartServices cartService;


    /*
     * =========================================================
     * GET CART
     * =========================================================
     *
     * GET:
     * /api/cart?customerId=1
     */
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @RequestParam Long customerId
    ) {

        System.out.println(
                "GET CART - customerId = "
                        + customerId
        );

        CartResponse response =
                cartService.getCart(
                        customerId
                );

        return ResponseEntity.ok(
                response
        );
    }


    /*
     * =========================================================
     * ADD ITEM TO CART
     * =========================================================
     *
     * POST:
     * /api/cart/items?customerId=1
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addToCart(

            @RequestParam Long customerId,

            @Valid
            @RequestBody
            AddToCartRequest request
    ) {

        System.out.println(
                "========== ADD TO CART =========="
        );

        System.out.println(
                "customerId = "
                        + customerId
        );

        System.out.println(
                "productId = "
                        + request.getProductId()
        );

        System.out.println(
                "packageId = "
                        + request.getPackageId()
        );

        System.out.println(
                "vehicleTypeId = "
                        + request.getVehicleTypeId()
        );

        System.out.println(
                "frequencyId = "
                        + request.getFrequencyId()
        );

        System.out.println(
                "quantity = "
                        + request.getQuantity()
        );


        CartResponse response =
                cartService.addToCart(
                        customerId,
                        request
                );


        return ResponseEntity.ok(
                response
        );
    }


    /*
     * =========================================================
     * UPDATE QUANTITY
     * =========================================================
     *
     * PUT:
     * /api/cart/items/{itemId}?customerId=1
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateQuantity(

            @PathVariable Long itemId,

            @RequestParam Long customerId,

            @Valid
            @RequestBody
            UpdateCartIteamRequest request
    ) {

        System.out.println(
                "UPDATE CART ITEM"
        );

        System.out.println(
                "customerId = "
                        + customerId
        );

        System.out.println(
                "itemId = "
                        + itemId
        );


        CartResponse response =
                cartService.updateQuantity(
                        customerId,
                        itemId,
                        request
                );


        return ResponseEntity.ok(
                response
        );
    }


    /*
     * =========================================================
     * REMOVE ONE ITEM
     * =========================================================
     *
     * DELETE:
     * /api/cart/items/{itemId}?customerId=1
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItem(

            @PathVariable Long itemId,

            @RequestParam Long customerId
    ) {

        System.out.println(
                "REMOVE CART ITEM"
        );

        System.out.println(
                "customerId = "
                        + customerId
        );

        System.out.println(
                "itemId = "
                        + itemId
        );


        CartResponse response =
                cartService.removeItem(
                        customerId,
                        itemId
                );


        return ResponseEntity.ok(
                response
        );
    }


    /*
     * =========================================================
     * CLEAR COMPLETE CART
     * =========================================================
     *
     * DELETE:
     * /api/cart?customerId=1
     */
    @DeleteMapping
    public ResponseEntity<Void> clearCart(

            @RequestParam Long customerId
    ) {

        System.out.println(
                "CLEAR CART - customerId = "
                        + customerId
        );


        cartService.clearCart(
                customerId
        );


        return ResponseEntity
                .noContent()
                .build();
    }
}