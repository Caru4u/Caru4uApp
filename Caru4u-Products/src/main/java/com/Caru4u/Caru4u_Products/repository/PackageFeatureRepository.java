package com.Caru4u.Caru4u_Products.repository;

import com.Caru4u.Caru4u_Products.entity.PackageFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PackageFeatureRepository
        extends JpaRepository<PackageFeature, Long> {

    List<PackageFeature>
    findByWashPackage_IdOrderByDisplayOrderAsc(Long packageId);
}
