package com.Caru4u.Caru4u_Products.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "caru4u_package_prices",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "package_id",
                                "vehicle_type_id",
                                "frequency_id"
                        }
                )
        }
)
public class PackagePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private WashPackage washPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleType vehicleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frequency_id", nullable = false)
    private Frequency frequency;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    private String currency;

    private Boolean active;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;
}