package com.sid.productservice.product.controller;

import com.sid.productservice.product.dto.ProductDTO;
import com.sid.productservice.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    private final ProductServiceImpl iProductService;

    public ProductController(ProductServiceImpl iProductService) {
        this.iProductService = iProductService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        LOGGER.info("Getting product By id request {}", id);
        return new ResponseEntity<>(iProductService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable("name") String name) {
        LOGGER.info("Getting product By id request {}", name);
        return new ResponseEntity<>(iProductService.getProductByName(name), HttpStatus.OK);
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) throws Exception {
        LOGGER.info("saving new product {}", productDTO.productName());
        return new ResponseEntity<>(iProductService.saveProduct(productDTO), HttpStatus.CREATED);
    }
}
