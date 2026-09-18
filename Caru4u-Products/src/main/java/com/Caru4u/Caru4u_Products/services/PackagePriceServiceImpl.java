package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import com.Caru4u.Caru4u_Products.exception.PackagePriceNotFoundException;
import com.Caru4u.Caru4u_Products.repository.PackagePriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PackagePriceServiceImpl implements PackagePriceService{

    private final PackagePriceRepository packagePriceRepository;

    public PackagePriceServiceImpl(
            PackagePriceRepository packagePriceRepository) {

        this.packagePriceRepository = packagePriceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PackagePriceResponse validatePackagePrice(
            Long productId,
            Long packageId,
            Long vehicleTypeId,
            Long frequencyId) {

        PackagePrice packagePrice =
                packagePriceRepository
                        .findValidPackagePrice(
                                productId,
                                packageId,
                                vehicleTypeId,
                                frequencyId
                        )
                        .orElseThrow(() ->
                                new PackagePriceNotFoundException(
                                        "No active package price found for " +
                                                "productId=" + productId +
                                                ", packageId=" + packageId +
                                                ", vehicleTypeId=" + vehicleTypeId +
                                                ", frequencyId=" + frequencyId
                                )
                        );

        return mapToResponse(packagePrice);
    }

    private PackagePriceResponse mapToResponse(
            PackagePrice packagePrice) {

        return PackagePriceResponse.builder()

                .productId(
                        packagePrice
                                .getWashPackage()
                                .getProduct()
                                .getId()
                )

                .productName(
                        packagePrice
                                .getWashPackage()
                                .getProduct()
                                .getName()
                )

                .packageId(
                        packagePrice
                                .getWashPackage()
                                .getId()
                )

                .packageName(
                        packagePrice
                                .getWashPackage()
                                .getName()
                )

                .vehicleTypeId(
                        packagePrice
                                .getVehicleType()
                                .getId()
                )

                .vehicleType(
                        packagePrice
                                .getVehicleType()
                                .getCode()
                )

                .frequencyId(
                        packagePrice
                                .getFrequency()
                                .getId()
                )

                .frequency(
                        packagePrice
                                .getFrequency()
                                .getName()
                )

                .price(packagePrice.getPrice())

                .currency(packagePrice.getCurrency())

                .active(packagePrice.getActive())

                .build();
    }
}
