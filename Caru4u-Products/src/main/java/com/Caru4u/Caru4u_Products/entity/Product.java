package com.Caru4u.Caru4u_Products.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "caru4u_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    private boolean active;

}
