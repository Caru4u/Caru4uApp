package com.Caru4u.Caru4u_Products.services;

import com.Caru4u.Caru4u_Products.entity.Product;
import com.Caru4u.Caru4u_Products.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements Products {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(){
        return productRepository.findByActiveTrue();
    }

}
