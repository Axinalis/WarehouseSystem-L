package com.axinalis.warehouse.consumer;

import com.axinalis.warehouse.service.ReportsService;
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
public class ReportsConsumer {

    private static Logger log = LoggerFactory.getLogger(ReportsConsumer.class);

    private ReportsService service;
    private ObjectMapper mapper;

    public ReportsConsumer(@Autowired ReportsService service, @Autowired ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "${REPORTS_TOPIC}", groupId = "${GROUP_ID}")
    public void listenReport(String report) {
        List<ChangeSetItem> items = parseMessageToList(report);
        log.info("Report from stores was received. Number of changed stocks is {}", items.size());
        service.processReport(items);
    }

    private List<ChangeSetItem> parseMessageToList(String message){
        try {
            return mapper.readValue(message, new TypeReference<List<ChangeSetItem>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error occurred while parsing report from stores: {}", e.getMessage());
            throw new RuntimeException("Error occurred while parsing report from stores");
        }
    }
}
