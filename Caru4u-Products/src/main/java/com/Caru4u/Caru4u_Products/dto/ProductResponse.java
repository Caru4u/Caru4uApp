package com.Caru4u.Caru4u_Products.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String description;

    private String imageUrl;
    private String bannerImageUrl;
}