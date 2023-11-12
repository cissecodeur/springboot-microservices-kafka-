package org.sid.order.service.impl;

import org.sid.order.dto.OrderDTO;
import org.sid.order.dto.OrderEvent;
import org.sid.order.entity.Order;
import org.sid.order.mapper.OrderMapper;
import org.sid.order.repository.OrderRepository;
import org.sid.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OrderImpl  implements IOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderImpl.class);
    private final KafkaTemplate<String , Object> kafkaTemplate;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper = new OrderMapper();

    private final String KAFKA_ORDER_TOPIC;

    public OrderImpl(KafkaTemplate<String, Object> kafkaTemplate, OrderRepository orderRepository, @Value("${spring.kafka.topic.name}") String KAFKA_ORDER_TOPIC) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
        this.KAFKA_ORDER_TOPIC = KAFKA_ORDER_TOPIC;
    }

    public void sendMessageToKafka(OrderEvent orderEvent){
            LOGGER.info("----- Order in the event {} ----- ", String.valueOf(orderEvent));
            kafkaTemplate.send(KAFKA_ORDER_TOPIC,orderEvent);
    }

    /**
     * @param orderDTO
     */
    @Override
    public OrderDTO update(Long id, OrderDTO orderDTO) {
         getOrderById(id);
         Order orderToSave = orderRepository.save(orderMapper.toEntity(orderDTO));
         return orderMapper.toDto(orderToSave);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public OrderDTO getOrderById(Long id) {
        return orderMapper.toDto(orderRepository.findById(id).orElseThrow(NullPointerException::new));
    }

    /**
     * @param orderDTO
     * @return
     */
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
         Order savedOrder = orderRepository.save(orderMapper.toEntity(orderDTO)) ;
        return orderMapper.toDto(savedOrder);
    }


}
