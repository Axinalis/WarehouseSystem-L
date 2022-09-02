package com.axinalis.store.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportSender {

    private static Logger log = LoggerFactory.getLogger(ReportSender.class);
    private int counter = 0;
    @Value("${REPORTS_TOPIC}")
    private String reportsTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 20000)
    public void sendNewReport(){
        log.info("1 - Creating new event to warehouse");
        kafkaTemplate.send(reportsTopic, "Message number " + ++counter);
        log.info("2 - Event to warehouse was created");
    }

}
