package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceUpdateRequest;

public interface CarWashServices {
    public CarWashPlanResponse getPlans(String vechileTpe);

    public PackagePriceResponse updatePrice(Long id, PackagePriceUpdateRequest packagePriceUpdateRequest);

    void deletePrice(Long id);

    }
