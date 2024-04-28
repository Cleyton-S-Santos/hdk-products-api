package com.hidq.products.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequestDTO {
    @NotNull
    private String productTitle;
    @NotNull
    private String productDesc;
    private String productImgUrl;
    @NotNull
    private Long productPrice;
    @NotNull
    private Long productStock;
}
