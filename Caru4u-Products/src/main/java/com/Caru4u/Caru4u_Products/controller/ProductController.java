package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.ProductResponse;
import com.Caru4u.Caru4u_Products.entity.Product;
import com.Caru4u.Caru4u_Products.services.ProductService;
import com.Caru4u.Caru4u_Products.services.Products;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Products products;

    public ProductController(Products products) {
        this.products = products;
    }


    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok( products.getProducts());
    }
}
