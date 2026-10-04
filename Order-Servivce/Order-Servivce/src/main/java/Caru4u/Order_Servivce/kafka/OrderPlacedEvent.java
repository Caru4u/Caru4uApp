package Caru4u.Order_Servivce.kafka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderPlacedEvent(

        Long orderId,

        Long customerId,

        BigDecimal totalAmount,

        String orderStatus,

        LocalDateTime createdAt

) {
}