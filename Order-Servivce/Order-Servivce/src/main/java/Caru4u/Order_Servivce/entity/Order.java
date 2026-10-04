package Caru4u.Order_Servivce.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "caru4u_order")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long customerId;


    @Column(nullable = false)
    private Long addressId;


    @Column(nullable = false)
    private LocalDate preferredDate;


    @Column(nullable = false)
    private String preferredTime;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;


    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal subtotal;


    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal discount;


    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal total;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


    @Column(nullable = false)
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
}