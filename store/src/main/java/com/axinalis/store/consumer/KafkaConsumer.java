package com.axinalis.store.consumer;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    private static Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    private ObjectMapper mapper;
    private StoreService service;

    public KafkaConsumer(@Autowired ObjectMapper mapper,
                         @Autowired StoreService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @KafkaListener(topics = "${RESPONSE_TOPIC}", groupId = "${GROUP_ID}")
    public void listenResponse(String truckWithItems){
        List<ChangeSetItem> items = parseMessageToList(truckWithItems);
        log.info("The truck with goods has arrived successfully");
        service.updateStocks(items);
    }

    private List<ChangeSetItem> parseMessageToList(String message){
        try {
            return mapper.readValue(message, new TypeReference<List<ChangeSetItem>>(){});
        } catch (JsonProcessingException e) {
            log.error("Error occurred while parsing report from stores: {}", e.getMessage());
            throw new RuntimeException("Error occurred while parsing report from stores");
        }
    }

}
