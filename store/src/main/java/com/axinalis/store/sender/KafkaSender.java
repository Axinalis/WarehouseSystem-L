package com.axinalis.store.sender;

import com.axinalis.store.changer.ChangeSetItem;
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
public class KafkaSender {

    private static Logger log = LoggerFactory.getLogger(KafkaSender.class);
    @Value("${REPORTS_TOPIC}")
    private String reportsTopic;

    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper;

    public KafkaSender() {
    }

    public KafkaSender(@Autowired KafkaTemplate<String, String> kafkaTemplate, @Autowired ObjectMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.mapper = mapper;
    }

    public void sendNewReport(List<ChangeSetItem> changeSet) {
        if(changeSet == null){
            throw new RuntimeException("Null pointer was passed instead of List");
        }
        try {
            kafkaTemplate.send(reportsTopic, mapper.writeValueAsString(changeSet));
        } catch (JsonProcessingException e) {
            log.error("Error while parsing changeSet to JSON format: {}", e.getMessage());
            throw new RuntimeException("Error while parsing changeSet to JSON format");
        }
    }
}
