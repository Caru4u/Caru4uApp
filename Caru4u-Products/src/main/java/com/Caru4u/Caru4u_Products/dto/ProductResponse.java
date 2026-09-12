package com.Caru4u.Caru4u_Products.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String code;

    private String name;

    private String description;

    private String imageUrl;
}