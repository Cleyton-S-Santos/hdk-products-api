package com.hidq.products.domain.entities;

import com.hidq.products.domain.dto.ProductRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "tb_products", schema = "hdk_database")
@Getter @Setter
@DynamicUpdate
public class ProductEntity {

    @Id
    @Column(name = "product_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;

    @Column(name = "product_title")
    String productTitle;

    @Column(name = "product_desc")
    String productDesc;

    @Column(name = "product_image_url")
    String productImgUrl;

    @Column(name = "product_price")
    Long productPrice;

    @Column(name = "product_stock")
    Long productStock;

    @Column(name = "created_at")
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    public static ProductEntity fromDto(ProductRequestDTO productEntity) {
        ProductEntity entity = new ProductEntity();
        entity.setProductTitle(productEntity.getProductTitle());
        entity.setProductDesc(productEntity.getProductDesc());
        entity.setProductImgUrl(productEntity.getProductImgUrl());
        entity.setProductPrice(productEntity.getProductPrice());
        entity.setProductStock(productEntity.getProductStock());
        return entity;
    }

}
