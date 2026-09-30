package com.Caru4u.Caru4u_Products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageResponse implements Serializable {

    private Long packageId;

    private String name;

    private String description;

    private boolean mostPopular;

    private List<PriceResponse> prices;

    private List<String> features;
}