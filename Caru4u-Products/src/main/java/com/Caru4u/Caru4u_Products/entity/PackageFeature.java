package com.Caru4u.Caru4u_Products.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "caru4u_package_features")
public class PackageFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private WashPackage washPackage;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "display_order")
    private Integer displayOrder;
}
