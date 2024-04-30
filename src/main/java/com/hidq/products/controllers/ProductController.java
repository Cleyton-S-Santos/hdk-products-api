package com.hidq.products.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hidq.products.domain.dto.ProductDTO;
import com.hidq.products.domain.dto.ProductRequestDTO;
import com.hidq.products.domain.entities.ProductEntity;
import com.hidq.products.exception.CustomException;
import com.hidq.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("product")
@Validated
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save")
    ProductDTO save(@RequestBody() ProductRequestDTO dto) throws CustomException {
        log.info("ProductController.save entry");
        return productService.create(dto);
    }

    @GetMapping("/find/{id}")
    public ProductDTO findById(@PathVariable Long id) throws CustomException {
        log.info("ProductController.findById entry");
        return productService.findById(id);
    }

    @GetMapping("list")
    public Page<ProductEntity> findAllPage(Pageable pageable){
        log.info("ProductController.findAllPage entry");
        return productService.findAllPageable(pageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) throws CustomException {
        log.info("ProductController.delete entry");
        return ResponseEntity.ok(productService.delete(id));
    }

    @PutMapping("/update/{id}")
    public ProductDTO patch(@Valid @RequestBody ProductRequestDTO productUpdateRequestDTO, @PathVariable Long id) throws JsonProcessingException {
        log.info("ProductController.patch entry");
        return productService.updateProduct(productUpdateRequestDTO, id);
    }

}
