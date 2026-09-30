package com.caru4u.Caru4u_Cart_Service.service;

import com.caru4u.Caru4u_Cart_Service.model.PackagePriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "product-service",
        url = "${product.service.url}"
)
public interface ProductServiceClient {

    @GetMapping("/api/car-wash/validate")
    PackagePriceResponse getPackagePrice(
            @RequestParam("productId") Long productId,
            @RequestParam("packageId") Long packageId,
            @RequestParam("vehicleTypeId") Long vehicleTypeId,
            @RequestParam("frequencyId") Long frequencyId
    );
}