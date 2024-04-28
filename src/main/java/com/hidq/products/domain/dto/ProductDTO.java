package com.hidq.products.domain.dto;

import com.hidq.products.domain.entities.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ProductDTO {
    private Long productId;
    private String productTitle;
    private String productDesc;
    private String productImgUrl;
    private Long productPrice;
    private Long productStock;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static ProductDTO fromEntity(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(productEntity.getProductId());
        dto.setProductTitle(productEntity.getProductTitle());
        dto.setProductDesc(productEntity.getProductDesc());
        dto.setProductImgUrl(productEntity.getProductImgUrl());
        dto.setProductPrice(productEntity.getProductPrice());
        dto.setProductStock(productEntity.getProductStock());
        dto.setCreatedAt(productEntity.getCreatedAt());
        dto.setUpdatedAt(productEntity.getUpdatedAt());
        return dto;
    }
}
