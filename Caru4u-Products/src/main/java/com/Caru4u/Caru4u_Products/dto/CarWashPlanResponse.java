package com.Caru4u.Caru4u_Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarWashPlanResponse {

    private String vehicleType;

    private List<PackageResponse> packages;
}