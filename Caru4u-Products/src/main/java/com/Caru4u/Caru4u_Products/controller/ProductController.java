package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.ProductResponse;
import com.Caru4u.Caru4u_Products.services.ProductsServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductsServices productsServices;

    public ProductController(ProductsServices productsServices) {
        this.productsServices = productsServices;
    }


    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok( productsServices.getProducts());
    }
}
