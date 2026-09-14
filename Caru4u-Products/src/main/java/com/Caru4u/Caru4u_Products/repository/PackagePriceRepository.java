package com.Caru4u.Caru4u_Products.repository;

import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackagePriceRepository
        extends JpaRepository<PackagePrice, Long> {

    List<PackagePrice>
    findByVehicleType_CodeAndActiveTrueOrderByWashPackage_DisplayOrderAsc(
            String vehicleTypeCode
    );

    List<PackagePrice> findByActiveTrue();
}