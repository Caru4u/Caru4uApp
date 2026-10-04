package Caru4u.Order_Servivce.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "caru4u_order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long orderId;


    @Column(nullable = false)
    private Long productId;


    private Long packageId;


    private String productName;

    private String packageName;

    private String vehicleType;

    private String frequency;


    @Column(nullable = false)
    private Integer quantity;


    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal unitPrice;


    @Column(
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal totalPrice;
}