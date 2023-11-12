package org.sid.order.service;

import org.sid.order.dto.OrderDTO;
import org.sid.order.dto.OrderEvent;

public interface IOrderService {

    void sendMessageToKafka(OrderEvent orderEvent);
    OrderDTO update (Long id, OrderDTO orderDTO);
    OrderDTO getOrderById(Long id);

    OrderDTO save (OrderDTO orderDTO);
}
