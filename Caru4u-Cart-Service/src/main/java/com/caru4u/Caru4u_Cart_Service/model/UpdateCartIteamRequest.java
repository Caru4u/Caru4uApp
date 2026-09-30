package com.caru4u.Caru4u_Cart_Service.model;

import jakarta.validation.constraints.Min;

public class UpdateCartIteamRequest {

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Min(value=1,message="Quantity must be at least 1")
    private Integer quantity;

}
