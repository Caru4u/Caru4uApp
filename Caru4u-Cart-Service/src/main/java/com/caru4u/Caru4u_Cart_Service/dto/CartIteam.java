package com.caru4u.Caru4u_Cart_Service.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caru4u_cart_iteam")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartIteam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "vehicle_type_id")
    private Long vehicleTypeId;

    // ADD THIS
    @Column(name = "frequency_id")
    private Long frequencyId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "frequency")
    private String frequency;

    @Column(nullable = false)
    private Integer quantity;

    @Column(
            name = "unit_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal unitPrice;

    @Column(
            name = "total_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}