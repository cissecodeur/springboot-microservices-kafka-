package org.sid.stock.feign;


import org.sid.stock.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9095/api/products/",name = "PRODUCT-SERVICE")
public interface CAllProductApi {


    @GetMapping("/{name}")
    ProductDTO getProductByName(@PathVariable("name") String name);


}
