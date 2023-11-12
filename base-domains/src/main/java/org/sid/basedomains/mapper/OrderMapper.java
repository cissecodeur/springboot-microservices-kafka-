package org.sid.basedomains.mapper;

import org.sid.basedomains.dto.OrderDTO;
import org.sid.basedomains.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {


   public OrderDTO toDto(Order order){
        return (order == null) ? null : new OrderDTO(
                order.getId(),
                order.getOrderId(),
                order.getOrderName(),
                order.getQty(),
                order.getPrice()

        );
    }

  public   Order toEntity(OrderDTO orderDTO){
        return (orderDTO == null) ? null : new Order(
                orderDTO.id(),
                orderDTO.orderId(),
                orderDTO.orderName(),
                orderDTO.qty(),
                orderDTO.price()

        );
    }

  public   List<OrderDTO> toDtos(List<Order> orders){
        return (orders == null) ? new ArrayList<>() :
                orders.stream()
                        .map(this::toDto)
                        .collect(Collectors.toList());
    }

 public   List<Order> toEntities(List<OrderDTO> orderDTOs){
        return (orderDTOs == null) ? new ArrayList<>() :
                orderDTOs.stream()
                         .map(this::toEntity)
                        .collect(Collectors.toList());
    }

}
