package com.hubt.th2501.product_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String subject;

    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Size> sizes;

    private int sold;

    private String description;

    private String imageUrl;
}
