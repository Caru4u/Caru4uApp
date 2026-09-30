package com.caru4u.Caru4u_Cart_Service.model;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NonNull;

@Data
public class AddToCartRequest {

    @NonNull
    private Long productId;

    private Long packageId;

    private Long vehicleTypeId;

    private Long frequencyId;

    @Min(1)
    private Integer quantity=1;

}
