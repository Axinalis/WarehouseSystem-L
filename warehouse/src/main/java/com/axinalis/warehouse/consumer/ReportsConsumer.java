package com.axinalis.warehouse.consumer;

import com.axinalis.warehouse.entity.StoreItemEntity;
import com.axinalis.warehouse.producer.ResponseSender;
import com.axinalis.warehouse.repository.StoreItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReportsConsumer {

    private static Logger log = LoggerFactory.getLogger(ReportsConsumer.class);
    @Autowired
    private ResponseSender responseSender;
    @Autowired
    StoreItemRepository repository;

    @KafkaListener(topics = "${REPORTS_TOPIC}", groupId = "${GROUP_ID}")
    public void listen(String message){
        StoreItemEntity entity = new StoreItemEntity();
        entity.setStoreId(1L);
        entity.setProductId(1L);
        entity.setMaxStock(100L);
        entity.setCurrentStock(55L);
        log.info("1 - Message from store was received: {}", message);
        repository.save(entity);
        responseSender.sendResponse("Response number " + message.replace("Message number ", ""));
        log.info("4 - Message with response was received to EventProducer");
    }

}
