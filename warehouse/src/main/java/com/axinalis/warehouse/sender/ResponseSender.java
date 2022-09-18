package com.axinalis.warehouse.sender;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseSender {

    private Logger log = LoggerFactory.getLogger(ResponseSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper mapper;
    @Value("${RESPONSE_TOPIC}")
    private String responseTopic;

    public void sendTruckWithGoods(List<ChangeSetItem> itemsToRefill){
        try {
            log.info("The truck with goods was sent");
            kafkaTemplate.send(responseTopic, mapper.writeValueAsString(itemsToRefill));
        } catch (JsonProcessingException e) {
            log.error("Error while sending a truck with goods to refill stocks: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
