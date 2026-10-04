package Caru4u.Order_Servivce.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CheckoutResponse {

    private Long cartId;

    private Long customerId;

    private AddressResponse address;

    private List<CheckoutItemResponse> items;

    private Integer itemCount;

    private BigDecimal subtotal;

    private BigDecimal discount;

    private BigDecimal total;
}