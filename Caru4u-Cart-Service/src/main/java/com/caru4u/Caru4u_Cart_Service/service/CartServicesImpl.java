package com.caru4u.Caru4u_Cart_Service.service;


import com.caru4u.Caru4u_Cart_Service.dto.Cart;
import com.caru4u.Caru4u_Cart_Service.dto.CartIteam;
import com.caru4u.Caru4u_Cart_Service.dto.CartStatus;
import com.caru4u.Caru4u_Cart_Service.excption.CartItemNotFoundException;
import com.caru4u.Caru4u_Cart_Service.model.*;
import com.caru4u.Caru4u_Cart_Service.repository.CartIteamRepository;
import com.caru4u.Caru4u_Cart_Service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServicesImpl implements CartServices {

    private final CartRepository cartRepository;

    private final CartIteamRepository cartItemRepository;

    private final ProductServiceClient productServiceClient;

    @Override
    @Transactional
    public CartResponse addToCart(
            Long customerId,
            AddToCartRequest request) {

        /*
         * 1. Find customer's active cart.
         *
         * If no cart exists → create cart.
         */
        Cart cart = cartRepository
                .findByCustomerIdAndCartStatus(
                        customerId,
                        CartStatus.ACTIVE
                )
                .orElseGet(() -> createCart(customerId));

        /*
         * 2. Get real product/package price
         * from Product Service.
         */
        PackagePriceResponse product =
                productServiceClient.getPackagePrice(
                        request.getProductId(),
                        request.getPackageId(),
                        request.getVehicleTypeId(),
                        request.getFrequencyId()
                );

        if (product == null ||
                Boolean.FALSE.equals(product.getActive())) {

            throw new IllegalArgumentException(
                    "Selected service is not available"
            );
        }

        /*
         * 3. Check whether same selection
         * already exists inside cart.
         */
        CartIteam existingItem =
                findMatchingItem(cart, request);

        if (existingItem != null) {

            Integer newQuantity =
                    existingItem.getQuantity()
                            + request.getQuantity();

            existingItem.setQuantity(newQuantity);

            existingItem.setUnitPrice(
                    product.getPrice()
            );

            existingItem.setTotalPrice(
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            newQuantity
                                    )
                            )
            );

        } else {

            CartIteam newItem =
                    CartIteam.builder()
                            .cart(cart)

                            .productId(
                                    product.getProductId()
                            )

                            .packageId(
                                    product.getPackageId()
                            )

                            .vehicleTypeId(
                                    product.getVehicleTypeId()
                            )

                            .frequencyId(
                                    product.getFrequencyId()
                            )

                            .productName(
                                    product.getProductName()
                            )

                            .packageName(
                                    product.getPackageName()
                            )

                            .vehicleType(
                                    product.getVehicleType()
                            )

                            .frequency(
                                    product.getFrequency()
                            )

                            .quantity(
                                    request.getQuantity()
                            )

                            .unitPrice(
                                    product.getPrice()
                            )

                            .totalPrice(
                                    product.getPrice()
                                            .multiply(
                                                    BigDecimal.valueOf(
                                                            request.getQuantity()
                                                    )
                                            )
                            )

                            .build();

            cart.getItems().add(newItem);
        }

        Cart savedCart =
                cartRepository.save(cart);

        return convertToResponse(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long customerId) {

        Cart cart =
                cartRepository
                        .findByCustomerIdAndCartStatus(
                                customerId,
                                CartStatus.ACTIVE
                        )
                        .orElse(null);

        if (cart == null) {

            return emptyCart(customerId);
        }

        return convertToResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateQuantity(
            Long customerId,
            Long cartItemId,
            UpdateCartIteamRequest request) {

        CartIteam item =
                cartItemRepository
                        .findByIdAndCart_CustomerId(
                                cartItemId,
                                customerId
                        )
                        .orElseThrow(
                                () ->
                                        new CartItemNotFoundException(
                                                "Cart item not found"
                                        )
                        );

        item.setQuantity(
                request.getQuantity()
        );

        item.setTotalPrice(
                item.getUnitPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        )
        );

        cartItemRepository.save(item);

        return convertToResponse(
                item.getCart()
        );
    }

    @Override
    @Transactional
    public CartResponse removeItem(
            Long customerId,
            Long cartItemId) {

        CartIteam item =
                cartItemRepository
                        .findByIdAndCart_CustomerId(
                                cartItemId,
                                customerId
                        )
                        .orElseThrow(
                                () ->
                                        new CartItemNotFoundException(
                                                "Cart item not found"
                                        )
                        );

        Cart cart = item.getCart();

        cart.getItems().remove(item);

        cartItemRepository.delete(item);

        return convertToResponse(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long customerId) {

        Cart cart =
                cartRepository
                        .findByCustomerIdAndCartStatus(
                                customerId,
                                CartStatus.ACTIVE
                        )
                        .orElse(null);

        if (cart != null) {

            cart.getItems().clear();

            cartRepository.save(cart);
        }
    }

    private Cart createCart(Long customerId) {

        Cart cart =
                Cart.builder()
                        .customerId(customerId)
                        .cartStatus(CartStatus.ACTIVE)
                        .items(new ArrayList<>())
                        .build();

        return cartRepository.save(cart);
    }

    private CartIteam findMatchingItem(
            Cart cart,
            AddToCartRequest request) {

        return cart.getItems()
                .stream()
                .filter(item ->
                        item.getProductId()
                                .equals(
                                        request.getProductId()
                                )
                )
                .filter(item ->
                        equals(
                                item.getPackageId(),
                                request.getPackageId()
                        )
                )
                .filter(item ->
                        equals(
                                item.getVehicleTypeId(),
                                request.getVehicleTypeId()
                        )
                )
                .filter(item ->
                        equals(
                                item.getFrequencyId(),
                                request.getFrequencyId()
                        )
                )
                .findFirst()
                .orElse(null);
    }

    private boolean equals(
            Object first,
            Object second) {

        if (first == null && second == null) {
            return true;
        }

        if (first == null || second == null) {
            return false;
        }

        return first.equals(second);
    }

    private CartResponse convertToResponse(
            Cart cart) {

        List<CartIteamResponse> items =
                cart.getItems()
                        .stream()
                        .map(item ->
                                CartIteamResponse.builder()

                                        .cartItemId(
                                                item.getId()
                                        )

                                        .productId(
                                                item.getProductId()
                                        )

                                        .packageId(
                                                item.getPackageId()
                                        )

                                        .productName(
                                                item.getProductName()
                                        )

                                        .packageName(
                                                item.getPackageName()
                                        )

                                        .vehicleType(
                                                item.getVehicleType()
                                        )

                                        .frequency(
                                                item.getFrequency()
                                        )

                                        .quantity(
                                                item.getQuantity()
                                        )

                                        .unitPrice(
                                                item.getUnitPrice()
                                        )

                                        .totalPrice(
                                                item.getTotalPrice()
                                        )

                                        .build()
                        )
                        .toList();

        BigDecimal subtotal =
                items.stream()
                        .map(
                                CartIteamResponse::getTotalPrice
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        /*
         * Later replace this with
         * Promotion/Coupon Service.
         */
        BigDecimal discount =
                BigDecimal.ZERO;

        BigDecimal total =
                subtotal.subtract(discount);

        int totalItems =
                items.stream()
                        .mapToInt(
                                CartIteamResponse::getQuantity
                        )
                        .sum();

        return CartResponse.builder()

                .cartId(cart.getId())

                .customerId(
                        cart.getCustomerId()
                )

                .totalItems(totalItems)

                .items(items)

                .subtotal(subtotal)

                .discount(discount)

                .total(total)

                .build();
    }

    private CartResponse emptyCart(
            Long customerId) {

        return CartResponse.builder()
                .customerId(customerId)
                .totalItems(0)
                .items(List.of())
                .subtotal(BigDecimal.ZERO)
                .discount(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .build();
    }
}