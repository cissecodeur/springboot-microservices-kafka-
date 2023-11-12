package org.sid.stock.service;

import org.sid.basedomains.dto.OrderEvent;

public interface IStockService {

            void getOrderToKafka(OrderEvent orderEvent);
            void update();
}
