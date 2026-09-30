package com.Caru4u.Caru4u_Products.repository;

import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PackagePriceRepository
        extends JpaRepository<PackagePrice, Long> {

    List<PackagePrice>
    findByVehicleType_CodeAndActiveTrueOrderByWashPackage_DisplayOrderAsc(
            String vehicleTypeCode
    );

    List<PackagePrice> findByActiveTrue();
    @Query("""
        SELECT pp
        FROM PackagePrice pp
        JOIN FETCH pp.washPackage wp
        JOIN FETCH wp.product p
        JOIN FETCH pp.vehicleType vt
        JOIN FETCH pp.frequency f
        WHERE p.id = :productId
          AND wp.id = :packageId
          AND vt.id = :vehicleTypeId
          AND f.id = :frequencyId
          AND pp.active = true
          AND wp.active = true
          AND p.active = true
          AND vt.active = true
        """)
    Optional<PackagePrice> findValidPackagePrice(
            @Param("productId") Long productId,
            @Param("packageId") Long packageId,
            @Param("vehicleTypeId") Long vehicleTypeId,
            @Param("frequencyId") Long frequencyId
    );
}