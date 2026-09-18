package com.caru4u.Caru4u_Cart_Service.excption;

public class CartNotFoundException extends RuntimeException{


    public CartNotFoundException(String message) {
        super(message);
    }
}
