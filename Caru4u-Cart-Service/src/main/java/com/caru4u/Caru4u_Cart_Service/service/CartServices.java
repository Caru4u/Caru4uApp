package com.caru4u.Caru4u_Cart_Service.service;

import com.caru4u.Caru4u_Cart_Service.model.AddToCartRequest;
import com.caru4u.Caru4u_Cart_Service.model.CartResponse;
import com.caru4u.Caru4u_Cart_Service.model.UpdateCartIteamRequest;

public interface CartServices {

    CartResponse getCart(Long customerId);

    CartResponse addToCart(
            Long customerId,
            AddToCartRequest request
    );

    CartResponse updateQuantity(
            Long customerId,
            Long cartItemId,
            UpdateCartIteamRequest request
    );

    CartResponse removeItem(
            Long customerId,
            Long cartItemId
    );

    void clearCart(Long customerId);
}
