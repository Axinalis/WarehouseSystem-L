package com.axinalis.store.consumer;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.Database;
import com.axinalis.store.runner.MainRunner;
import com.axinalis.store.sender.KafkaSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

    private static Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private KafkaSender sender;
    @Autowired
    private MainRunner runner;
    @Autowired
    private Database database;

    @KafkaListener(topics = "${RESPONSE_TOPIC}", groupId = "${GROUP_ID}")
    public void listenResponse(String truckWithItems){
        List<ChangeSetItem> items = parseMessageToList(truckWithItems);
        log.info("The truck with goods has arrived successfully");
        database.refillStocks(items);
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
