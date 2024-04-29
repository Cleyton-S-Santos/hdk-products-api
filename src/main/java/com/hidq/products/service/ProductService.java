package com.hidq.products.service;

import com.hidq.products.domain.dto.ProductDTO;
import com.hidq.products.domain.dto.ProductRequestDTO;
import com.hidq.products.domain.entities.ProductEntity;
import com.hidq.products.exception.CustomException;
import com.hidq.products.repository.ProductRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MinioServiceImpl minioService;

    public ProductDTO create(String data, MultipartFile file) throws CustomException {
        try{
            ProductRequestDTO requestToDto = objectMapper.readValue(data, ProductRequestDTO.class);
            ProductEntity parsed = ProductEntity.fromDto(requestToDto);
            parsed.setCreatedAt(LocalDate.now());
            parsed.setUpdatedAt(LocalDate.now());

            parsed.setProductImgUrl(minioService.save(file));
            ProductEntity response = productRepository.save(parsed);
            return ProductDTO.fromEntity(response);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public ProductDTO findById(Long id) throws CustomException {
        ProductEntity response = productRepository.findById(id).orElseThrow(() -> new CustomException("Usuario não encontrado", HttpStatus.NOT_FOUND.value()));
        return ProductDTO.fromEntity(response);
    }

    public String delete(Long id) throws CustomException {
        ProductEntity find = productRepository.findById(id).orElseThrow(() -> new CustomException("Usuario não encontrado", HttpStatus.NOT_FOUND.value()));
        productRepository.deleteById(id);
        return "Deleted";
    }

    public Page<ProductEntity> findAllPageable(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public ProductDTO updateProduct(ProductRequestDTO dto, Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow();
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(dto, productEntity);

        productEntity.setUpdatedAt(LocalDate.now());
        return ProductDTO.fromEntity(productRepository.save(productEntity));
    }
}
