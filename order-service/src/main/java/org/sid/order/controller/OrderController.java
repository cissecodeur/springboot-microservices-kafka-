package org.sid.order.controller;
import lombok.extern.slf4j.Slf4j;
import org.sid.order.dto.OrderDTO;
import org.sid.order.dto.OrderEvent;
import org.sid.order.enums.EventType;
import org.sid.order.enums.OrderStatus;
import org.sid.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final IOrderService iOrderService;

    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }


    @PostMapping("/create")
    public String placeOrder(@RequestBody OrderDTO orderDTO){
        LOGGER.info("------- Sending request to kafka ---------");
          OrderDTO orderDTOtoSave = new OrderDTO(
                  orderDTO.id(),
                  UUID.randomUUID().toString(),
                  orderDTO.orderName(),
                  orderDTO.qty(),
                  orderDTO.price()
        );

           OrderEvent orderDTOToSendToKafka = new OrderEvent(
                     "new order placement",
                      EventType.CREATE.name(),
                      OrderStatus.PENDING.name(),
                      orderDTOtoSave);
        iOrderService.sendMessageToKafka(orderDTOToSendToKafka);
           return "order placed successfully";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        LOGGER.info("------- Get order by Id {} ---------",id);
         return new ResponseEntity<>(iOrderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody OrderDTO orderDTO){
        LOGGER.info("------- save order {} ---------", orderDTO.orderId());
        return new ResponseEntity<>(iOrderService.save(orderDTO), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id,@RequestBody OrderDTO orderDTO){
        LOGGER.info("------- Update order {} ---------", orderDTO.orderId());
        return new ResponseEntity<>(iOrderService.update(id,orderDTO), HttpStatus.OK);
    }
}
