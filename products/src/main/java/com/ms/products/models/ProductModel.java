package com.ms.products.models;

import com.ms.products.dtos.ProductDTO;
import com.ms.products.repositories.ProductRepository;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_products")
@Getter
@Builder
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private BigDecimal value;

    public ProductModel() {
        //FOR JPA-HIBERNATE
    }

    public ProductModel(UUID id, String name, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public void update(ProductDTO product) {
        this.name = product.getName();
        this.value = product.getValue();
    }
}
