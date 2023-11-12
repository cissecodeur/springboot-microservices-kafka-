package org.sid.stock.feign;


import org.sid.basedomains.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(url = "http://localhost:8081/api/orders/",name = "ORDER-SERVICE")
public interface CAllOrderApi {

    @GetMapping("/{id}")
     ResponseEntity<?> getOrderById(@PathVariable("id") Long id);

    @PostMapping("/save")
     ResponseEntity<?> save(@RequestBody OrderDTO orderDTO);

    @PutMapping("/update/{id}")
     ResponseEntity<?> getOrderById(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO);

}
