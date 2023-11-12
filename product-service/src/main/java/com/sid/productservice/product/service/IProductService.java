package com.sid.productservice.product.service;

import com.sid.productservice.product.dto.ProductDTO;

public interface IProductService {

    ProductDTO getProductById(Long id);

    ProductDTO getProductByName(String name);

    ProductDTO saveProduct(ProductDTO productDTO) throws Exception;
}
