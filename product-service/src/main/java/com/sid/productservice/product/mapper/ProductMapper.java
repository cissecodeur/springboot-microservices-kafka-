package com.sid.productservice.product.mapper;

import com.sid.productservice.product.dto.ProductDTO;
import com.sid.productservice.product.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {


    public  ProductDTO toDto(Product product){
        return (product==null) ? null : new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getCreatedAt(),
                product.isAvailable(),
                product.isDeleted()
        );
    }

    public Product toEntity(ProductDTO productDTO){
        return (productDTO==null) ? null : new Product(
                productDTO.id(),
                productDTO.productName(),
                productDTO.price(),
                productDTO.createdAt(),
                productDTO.isAvailable(),
                productDTO.isDeleted()
        );
    }

    public List<ProductDTO> toDtos(List<Product> products){
        return (products == null) ? new ArrayList<>() :
                products.stream()
                        .map(this ::toDto)
                        .collect(Collectors.toList());
    }

    public List<Product> toEntities(List<ProductDTO> productDTOS){
        return (productDTOS==null) ? new ArrayList<>():
                productDTOS.stream()
                        .map(this::toEntity)
                        .collect(Collectors.toList());

    }

}
