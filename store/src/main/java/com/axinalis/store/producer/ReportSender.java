package com.axinalis.store.producer;

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
public class ReportSender {

    private static Logger log = LoggerFactory.getLogger(ReportSender.class);
    @Value("${REPORTS_TOPIC}")
    private String reportsTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper mapper;

    public void sendNewReport(List<ChangeSetItem> changeSet){
        try {
            kafkaTemplate.send(reportsTopic, mapper.writeValueAsString(changeSet));
        } catch (JsonProcessingException e) {
            log.error("Error while parsing changeSet to JSON format: {}", e.getMessage());
            throw new RuntimeException("Error while parsing changeSet to JSON format");
        }
    }

}
