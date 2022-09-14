package com.axinalis.warehouse.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResponseSender {

    private Logger log = LoggerFactory.getLogger(ResponseSender.class);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${RESPONSE_TOPIC}")
    private String responseTopic;

    public void sendResponse(String message){
        kafkaTemplate.send(responseTopic, "The response: " + message);
    }

}
