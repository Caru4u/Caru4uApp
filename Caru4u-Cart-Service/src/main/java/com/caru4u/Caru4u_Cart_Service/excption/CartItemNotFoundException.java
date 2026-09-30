package com.caru4u.Caru4u_Cart_Service.excption;

public class CartItemNotFoundException extends RuntimeException
{
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
