package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceUpdateRequest;
import com.Caru4u.Caru4u_Products.dto.PackageResponse;
import com.Caru4u.Caru4u_Products.dto.PriceResponse;

import com.Caru4u.Caru4u_Products.entity.PackageFeature;
import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import com.Caru4u.Caru4u_Products.entity.WashPackage;

import com.Caru4u.Caru4u_Products.repository.PackageFeatureRepository;
import com.Caru4u.Caru4u_Products.repository.PackagePriceRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CarWashServicesImpl
        implements CarWashServices {

    private final PackagePriceRepository packagePriceRepository;

    private final PackageFeatureRepository packageFeatureRepository;


    // ============================================================
    // GET CAR WASH PLANS
    // ============================================================

    @Override
    @Cacheable(
            value = "carWashPlans",
            key = "#vehicleType.toUpperCase()"
    )
    @Transactional(readOnly = true)
    public CarWashPlanResponse getPlans(
            String vehicleType
    ) {

        System.out.println(
                "DATABASE CALLED FOR VEHICLE TYPE: "
                        + vehicleType
        );


        // HATCHBACK / MID_SUV / SUV etc.
        String vehicleCode =
                vehicleType
                        .trim()
                        .toUpperCase()
                        .replace("-", "_")
                        .replace(" ", "_");


        /*
         * Find all active package prices
         * for selected vehicle type.
         */
        List<PackagePrice> packagePrices =
                packagePriceRepository
                        .findByVehicleType_CodeAndActiveTrueOrderByWashPackage_DisplayOrderAsc(
                                vehicleCode
                        );


        if (packagePrices.isEmpty()) {

            throw new RuntimeException(
                    "No plans found for vehicle: "
                            + vehicleCode
            );
        }


        /*
         * Group prices by package.
         *
         * Example:
         *
         * Life
         *   Daily
         *   Weekly
         *   Alternate Days
         *
         * Premium
         *   Daily
         *   Weekly
         *   Alternate Days
         */
        Map<Long, List<PackagePrice>> groupedPrices =
                packagePrices
                        .stream()
                        .collect(
                                Collectors.groupingBy(
                                        price ->
                                                price
                                                        .getWashPackage()
                                                        .getId(),

                                        LinkedHashMap::new,

                                        Collectors.toList()
                                )
                        );


        /*
         * Convert database entities
         * into frontend response.
         */
        List<PackageResponse> packageResponses =
                groupedPrices
                        .values()
                        .stream()
                        .map(this::convertPackageResponse)
                        .toList();


        return CarWashPlanResponse
                .builder()
                .vehicleType(vehicleCode)
                .packages(packageResponses)
                .build();
    }


    // ============================================================
    // UPDATE PRICE
    // ============================================================

    @Override
    @CacheEvict(
            value = {
                    "packagePrices",
                    "carWashPlans"
            },
            allEntries = true
    )
    public PackagePriceResponse updatePrice(
            Long id,
            PackagePriceUpdateRequest request
    ) {

        PackagePrice packagePrice =
                packagePriceRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Package Price Not Found"
                                        )
                        );


        packagePrice.setPrice(
                request.getPrice()
        );


        PackagePrice updated =
                packagePriceRepository.save(
                        packagePrice
                );


        return convertToResponse(
                updated
        );
    }


    // ============================================================
    // DELETE PRICE
    // ============================================================

    @Override
    @CacheEvict(
            value = {
                    "packagePrices",
                    "carWashPlans"
            },
            allEntries = true
    )
    public void deletePrice(
            Long id
    ) {

        PackagePrice packagePrice =
                packagePriceRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Package Price Not Found"
                                        )
                        );


        packagePriceRepository.delete(
                packagePrice
        );


        System.out.println(
                "PRICE DELETED FROM DATABASE"
        );
    }


    // ============================================================
    // VALIDATE PRICE
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public PackagePriceResponse validatePrice(

            Long productId,
            Long packageId,
            Long vehicleTypeId,
            Long frequencyId
    ) {

        System.out.println(
                "========== VALIDATE PACKAGE PRICE =========="
        );

        System.out.println(
                "productId = " + productId
        );

        System.out.println(
                "packageId = " + packageId
        );

        System.out.println(
                "vehicleTypeId = " + vehicleTypeId
        );

        System.out.println(
                "frequencyId = " + frequencyId
        );


        PackagePrice packagePrice =
                packagePriceRepository
                        .findByWashPackage_IdAndVehicleType_IdAndFrequency_IdAndActiveTrue(
                                packageId,
                                vehicleTypeId,
                                frequencyId
                        )
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Package price not found. "
                                                        + "packageId="
                                                        + packageId
                                                        + ", vehicleTypeId="
                                                        + vehicleTypeId
                                                        + ", frequencyId="
                                                        + frequencyId
                                        )
                        );


        return PackagePriceResponse
                .builder()

                .id(
                        packagePrice.getId()
                )

                /*
                 * PackagePrice does not currently
                 * contain Product relationship.
                 *
                 * Therefore use the productId
                 * received from Cart Service.
                 */
                .productId(
                        productId
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
                                .getName()
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

                .price(
                        packagePrice.getPrice()
                )

                .currency(
                        packagePrice.getCurrency()
                )

                .active(
                        packagePrice.getActive()
                )

                .validFrom(
                        packagePrice.getValidFrom()
                )

                .validTo(
                        packagePrice.getValidTo()
                )

                .build();
    }


    // ============================================================
    // CONVERT PACKAGE PRICE ENTITY → DTO
    // ============================================================

    private PackagePriceResponse convertToResponse(
            PackagePrice price
    ) {

        return PackagePriceResponse
                .builder()

                .id(
                        price.getId()
                )

                .packageId(
                        price
                                .getWashPackage()
                                .getId()
                )

                .packageName(
                        price
                                .getWashPackage()
                                .getName()
                )

                .vehicleTypeId(
                        price
                                .getVehicleType()
                                .getId()
                )

                .vehicleType(
                        price
                                .getVehicleType()
                                .getName()
                )

                .frequencyId(
                        price
                                .getFrequency()
                                .getId()
                )

                .frequency(
                        price
                                .getFrequency()
                                .getName()
                )

                .price(
                        price.getPrice()
                )

                .currency(
                        price.getCurrency()
                )

                .active(
                        price.getActive()
                )

                .validFrom(
                        price.getValidFrom()
                )

                .validTo(
                        price.getValidTo()
                )

                .build();
    }


    // ============================================================
    // CONVERT PACKAGE + PRICES FOR FRONTEND
    // ============================================================

    private PackageResponse convertPackageResponse(
            List<PackagePrice> packagePrices
    ) {

        /*
         * Every price in this list belongs
         * to the same WashPackage.
         */
        WashPackage washPackage =
                packagePrices
                        .get(0)
                        .getWashPackage();


        /*
         * THIS IS THE IMPORTANT FIX.
         *
         * Earlier:
         *
         * frequency
         * description
         * price
         *
         * were returned.
         *
         * Now we additionally return:
         *
         * frequencyId
         * vehicleTypeId
         *
         * React needs these IDs when
         * adding an item to Cart.
         */
        List<PriceResponse> prices =
                packagePrices
                        .stream()
                        .map(
                                packagePrice ->

                                        PriceResponse
                                                .builder()

                                                .frequencyId(
                                                        packagePrice
                                                                .getFrequency()
                                                                .getId()
                                                )

                                                .vehicleTypeId(
                                                        packagePrice
                                                                .getVehicleType()
                                                                .getId()
                                                )

                                                .frequency(
                                                        packagePrice
                                                                .getFrequency()
                                                                .getName()
                                                )

                                                .description(
                                                        packagePrice
                                                                .getFrequency()
                                                                .getDescription()
                                                )

                                                .price(
                                                        packagePrice
                                                                .getPrice()
                                                )

                                                .build()
                        )
                        .toList();


        /*
         * Get package features.
         */
        List<String> features =
                packageFeatureRepository
                        .findByWashPackage_IdOrderByDisplayOrderAsc(
                                washPackage.getId()
                        )
                        .stream()
                        .map(
                                PackageFeature::getFeatureName
                        )
                        .toList();


        return PackageResponse
                .builder()

                .packageId(
                        washPackage.getId()
                )

                .name(
                        washPackage.getName()
                )

                .description(
                        washPackage.getDescription()
                )

                .mostPopular(
                        Boolean.TRUE.equals(
                                washPackage.getMostPopular()
                        )
                )

                .prices(
                        prices
                )

                .features(
                        features
                )

                .build();
    }
}