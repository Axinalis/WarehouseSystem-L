package com.axinalis.store.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseConsumer {

    private static Logger log = LoggerFactory.getLogger(ResponseConsumer.class);

    @KafkaListener(topics = "${RESPONSE_TOPIC}", groupId = "${GROUP_ID}")
    public void listen(String message){
        log.info("5 - Message from warehouse was received: {}", message);
    }

}
