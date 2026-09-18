package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;

public interface PackagePriceService {
    PackagePriceResponse validatePackagePrice(
            Long productId,
            Long packageId,
            Long vehicleTypeId,
            Long frequencyId
    );
}
