package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.dto.ProductResponse;
import com.Caru4u.Caru4u_Products.entity.Product;
import com.Caru4u.Caru4u_Products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServicesImpl implements ProductsServices {

    private final ProductRepository productRepository;

    @Override
    @Cacheable(value = "products",key = "'allProducts'")
    public List<ProductResponse> getProducts() {

        System.out.println("DATABASE CALLED FOR PRODUCTS");
        return productRepository
                .findByActiveTrue()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ProductResponse convertToResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl()).
                bannerImageUrl(product.getBannerImageUrl())
                .build();
    }
}