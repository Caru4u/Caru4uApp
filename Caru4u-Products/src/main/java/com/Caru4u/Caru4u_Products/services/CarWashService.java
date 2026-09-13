package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackageResponse;
import com.Caru4u.Caru4u_Products.dto.PriceResponse;
import com.Caru4u.Caru4u_Products.entity.PackageFeature;
import com.Caru4u.Caru4u_Products.entity.PackagePrice;
import com.Caru4u.Caru4u_Products.entity.WashPackage;
import com.Caru4u.Caru4u_Products.repository.PackageFeatureRepository;
import com.Caru4u.Caru4u_Products.repository.PackagePriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarWashService implements CarWash {

    private final PackagePriceRepository packagePriceRepository;
    private final PackageFeatureRepository packageFeatureRepository;

    @Override
    public CarWashPlanResponse getPlans(String vehicleType) {

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