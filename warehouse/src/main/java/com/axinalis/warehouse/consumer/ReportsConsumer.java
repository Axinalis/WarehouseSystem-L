package com.axinalis.warehouse.consumer;

import com.axinalis.warehouse.producer.ResponseSender;
import com.axinalis.warehouse.repository.StoreItemRepository;
import com.axinalis.warehouse.service.ReportsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReportsConsumer {

    private static Logger log = LoggerFactory.getLogger(ReportsConsumer.class);
    @Autowired
    private ResponseSender responseSender;
    @Autowired
    StoreItemRepository repository;
    @Autowired
    private ReportsService service;
    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = "${REPORTS_TOPIC}", groupId = "${GROUP_ID}")
    public void listen(String message) {
        List<ChangeSetItem> items = parseMessageToList(message);

        log.info("Report from stores was received. Number of changed stocks is {}", items.size());
        service.processReport(items);
    }

    private List<ChangeSetItem> parseMessageToList(String message){
        try {
            JsonNode node = mapper.readTree(message);
            List<ChangeSetItem> items = new ArrayList<>();

            for(int i = 0; node.has(i + 1); i++){
                ChangeSetItem item = new ChangeSetItem();
                if(populateItem(item, node.get(i))){
                    items.add(item);
                }
            }
            return items;
        } catch (JsonProcessingException e) {
            log.error("Error occurred while parsing report from stores: {}", e.getMessage());
            throw new RuntimeException("Error occurred while parsing report from stores");
        }
    }

    private boolean populateItem(ChangeSetItem item, JsonNode node){
       try{
           item.setProductId(node.get("productId").asLong());
           item.setStoreId(node.get("storeId").asLong());
           item.setCategoryId(node.get("categoryId").asLong());
           item.setCurrentStock(node.get("currentStock").asLong());
           return true;
       } catch(NullPointerException e){
           log.warn("An item from report came with not enough data. Skipping it");
           return false;
       }
    }

}
