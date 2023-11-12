package org.sid.stock.service.Impl;

import org.hibernate.generator.EventType;
import org.sid.basedomains.dto.OrderDTO;
import org.sid.basedomains.dto.OrderEvent;
import org.sid.stock.dto.ProductDTO;
import org.sid.stock.entity.Stock;
import org.sid.stock.enums.OrderStatus;
import org.sid.stock.feign.CAllOrderApi;
import org.sid.stock.feign.CAllProductApi;
import org.sid.stock.repository.StockRepository;
import org.sid.stock.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class StockServiceImpl implements IStockService {

     private final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);
     private final StockRepository stockRepository;
     private final CAllProductApi cAllProductApi;
    private final CAllOrderApi cAllOrderApi;


    @Value("${spring.kafka.topic.success.name}")
    private String  topicSuccessName;

    @Value("${spring.kafka.topic.failed.name}")
    private String  topicFailName;

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public StockServiceImpl(StockRepository stockRepository, CAllProductApi cAllProductApi, CAllOrderApi cAllOrderApi, KafkaTemplate<String, Object> kafkaTemplate) {
        this.stockRepository = stockRepository;
        this.cAllProductApi = cAllProductApi;
        this.cAllOrderApi = cAllOrderApi;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * OrderEvent param to get in the Kafka topic
     */
    @Override
    @KafkaListener(
            topics = "${spring.kafka.topic.pending.name}",
            groupId = "${spring.kafka.consumer.group.id}"
    )
    public void getOrderToKafka(OrderEvent orderEvent) {
        LOGGER.info("------ Getting order from kafka {} -----",orderEvent);

        // recuperer le product dans orderEvent
        ProductDTO productDTO = cAllProductApi.getProductByName(orderEvent.orderDTO().orderName());

        double qtyInStock  = getStockQuantity(productDTO.id()); // Verify if the Product is available in stock
        orderingFailed(qtyInStock,orderEvent); // ordering failed case
        orderingSucced(qtyInStock,orderEvent);
         //update stock

    }

    /**
     *
     */
    @Override
    public void update() {

    }

    private  double getStockQuantity(Long productId){
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(NullPointerException::new);
        return stock.getQuantity();
    }

    public void sendMessageToKafka(String topicName,OrderEvent orderEvent){
        LOGGER.info("----- Order in the event {} ----- ", String.valueOf(orderEvent));
        kafkaTemplate.send(topicName,orderEvent);
    }

    void orderingFailed(double qtyInStock,OrderEvent orderEvent){
        // if not ok then publish event to kafka with not available and save the order with FAILED status in db
        if (qtyInStock < orderEvent.orderDTO().qty()){
            OrderEvent orderDTOToSendToKafka = new OrderEvent(
                    "Order placement failed",
                    EventType.UPDATE.name(),
                    OrderStatus.FAILED.name(),
                    orderEvent.orderDTO());

            sendMessageToKafka(topicFailName, orderDTOToSendToKafka);

            OrderDTO orderDTOToUpdate = new OrderDTO(
                    null,
                    orderEvent.orderDTO().orderId(),
                    orderEvent.orderDTO().orderName(),
                    orderEvent.orderDTO().qty(),
                    orderEvent.orderDTO().price()
            );

            cAllOrderApi.save(orderDTOToUpdate);

        }
    }

    void orderingSucced(double qtyInStock,OrderEvent orderEvent){
        // if  ok then publish event to kafka with ACCEPTED status and save the order in db
        if (qtyInStock >= orderEvent.orderDTO().qty()){
            OrderEvent orderDTOToSendToKafka = new OrderEvent(
                    "Order placement success",
                    EventType.UPDATE.name(),
                    OrderStatus.PROCESS.name(),
                    orderEvent.orderDTO());

            sendMessageToKafka(topicSuccessName, orderDTOToSendToKafka);

            OrderDTO orderDTOToUpdate = new OrderDTO(
                    null,
                    orderEvent.orderDTO().orderId(),
                    orderEvent.orderDTO().orderName(),
                    orderEvent.orderDTO().qty(),
                    orderEvent.orderDTO().price()
            );

            cAllOrderApi.save(orderDTOToUpdate);
        }

    }


}
