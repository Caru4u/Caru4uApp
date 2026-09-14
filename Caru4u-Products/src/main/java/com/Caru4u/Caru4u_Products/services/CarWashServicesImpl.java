package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.*;
import com.Caru4u.Caru4u_Products.entity.PackageFeature;
import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import com.Caru4u.Caru4u_Products.entity.WashPackage;
import com.Caru4u.Caru4u_Products.repository.PackageFeatureRepository;
import com.Caru4u.Caru4u_Products.repository.PackagePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarWashServicesImpl implements CarWashServices {

    private final PackagePriceRepository packagePriceRepository;
    private final PackageFeatureRepository packageFeatureRepository;

    @Override
    @Cacheable(value = "carWashPlans",key = "'#vehicleType.toUpperCase()'")
    public CarWashPlanResponse getPlans(String vehicleType) {

        System.out.println("DATABASE CALLED FOR VECHICLE TYPE:"+vehicleType);
        String vehicleCode = vehicleType
                .trim()
                .toUpperCase()
                .replace("-", "_")
                .replace(" ", "_");

        List<PackagePrice> packagePrices =
                packagePriceRepository
                        .findByVehicleType_CodeAndActiveTrueOrderByWashPackage_DisplayOrderAsc(
                                vehicleCode
                        );

        if (packagePrices.isEmpty()) {
            throw new RuntimeException(
                    "No plans found for vehicle: " + vehicleCode
            );
        }

        Map<Long, List<PackagePrice>> groupedPrices =
                packagePrices.stream()
                        .collect(
                                Collectors.groupingBy(
                                        price -> price.getWashPackage().getId(),
                                        LinkedHashMap::new,
                                        Collectors.toList()
                                )
                        );

        List<PackageResponse> packageResponses =
                groupedPrices.values()
                        .stream()
                        .map(this::convertPackageResponse)
                        .toList();

        return CarWashPlanResponse.builder()
                .vehicleType(vehicleCode)
                .packages(packageResponses)
                .build();
    }

    @Override
    @CacheEvict(value = "packagePrices",key = "'allPrices'")
    public PackagePriceResponse updatePrice(Long id, PackagePriceUpdateRequest packagePriceUpdateRequest) {
        PackagePrice packagePrice=packagePriceRepository.findById(id).
                orElseThrow(()->new RuntimeException("Package Price Not Found"));
        packagePrice.setPrice(
                packagePriceUpdateRequest.getPrice()
        );
       PackagePrice  update= packagePriceRepository.save(packagePrice);
        return convertToResponse(update);
    }

    @Override
    public void deletePrice(Long id) {
     PackagePrice packagePrice=packagePriceRepository.findById(id).
             orElseThrow(()->new RuntimeException("Package Price Not Found"));
     packagePriceRepository.delete(packagePrice);
        System.out.println("PRICE DELETED FROM DATABASE");
    }

    private PackagePriceResponse convertToResponse(PackagePrice price) {

        return PackagePriceResponse.builder()
                .id(price.getId())
                .packageId(price.getWashPackage().getId())
                .vehicleTypeId(price.getVehicleType().getId())
                .frequencyId(price.getFrequency().getId())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .active(price.getActive())
                .validFrom(price.getValidFrom())
                .validTo(price.getValidTo())
                .build();
    }

    private PackageResponse convertPackageResponse(
            List<PackagePrice> packagePrices
    ) {

        WashPackage washPackage =
                packagePrices.get(0).getWashPackage();

        List<PriceResponse> prices =
                packagePrices.stream()
                        .map(packagePrice ->
                                PriceResponse.builder()
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
                                                packagePrice.getPrice()
                                        )
                                        .build()
                        )
                        .toList();

        List<String> features =
                packageFeatureRepository
                        .findByWashPackage_IdOrderByDisplayOrderAsc(
                                washPackage.getId()
                        )
                        .stream()
                        .map(PackageFeature::getFeatureName)
                        .toList();

        return PackageResponse.builder()
                .packageId(washPackage.getId())
                .name(washPackage.getName())
                .description(washPackage.getDescription())
                .mostPopular(
                        Boolean.TRUE.equals(
                                washPackage.getMostPopular()
                        )
                )
                .prices(prices)
                .features(features)
                .build();
    }
}