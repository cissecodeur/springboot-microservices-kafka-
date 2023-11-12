package com.sid.productservice.product.service.impl;

import com.sid.productservice.product.dto.ProductDTO;
import com.sid.productservice.product.entity.Product;
import com.sid.productservice.product.mapper.ProductMapper;
import com.sid.productservice.product.repository.ProductRepository;

import com.sid.productservice.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    ProductMapper productMapper = new ProductMapper();

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl( ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //ici je vais ajouter un produit
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NullPointerException::new);
        return productMapper.toDto(product);
    }

    /**
     * @param name
     * @return
     */
    @Override
    public ProductDTO getProductByName(String name) {
        Optional<Product> product = Optional.ofNullable(productRepository.findByProductName(name).orElseThrow(NullPointerException::new));
        return productMapper.toDto(product.get());
    }

    /**
     * @param productDTO
     * @return productDTO
     */
    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws Exception {

        productExistingInBd(productDTO.productName());
        Product productToSave = productMapper.toEntity(productDTO);
        productToSave.setCreatedAt(LocalDateTime.now());
        productRepository.save(productToSave);
        return productMapper.toDto(productToSave);
    }

    private void productExistingInBd(String name){
       Optional<Product> product =  productRepository.findByProductName(name);
       if(product.isPresent()){
           throw new RuntimeException("Product already exist");
       }
      }

    private void productNotExistInBd(String name){
        Optional<Product> product =  productRepository.findByProductName(name);
        if(product.isEmpty()){
            throw new RuntimeException("Product Not found in db");
        }
    }


}
