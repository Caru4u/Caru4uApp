package com.Caru4u.Caru4u_Products.repository;

import com.Caru4u.Caru4u_Products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    Optional<Product> findByCode(Long aLong);
}
