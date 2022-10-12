package com.axinalis.warehouse.consumer;

import com.axinalis.warehouse.TestData;
import com.axinalis.warehouse.service.impl.ReportsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReportsConsumerTest {

    private static ObjectMapper mapper;
    private static ReportsConsumer consumer;
    private static ReportsServiceImpl service;

    @BeforeAll
    public static void setup(){
        mapper = new ObjectMapper();
        service = Mockito.mock(ReportsServiceImpl.class);
        consumer = new ReportsConsumer(service, mapper);
    }

    @Test
    public void testReceivingValidMessage(){
        // given
        String message = TestData.getChangeSetAsJson();
        List<ChangeSetItem> items = TestData.getChangeSet();

        // when
        consumer.listenReport(message);

        // then
        Mockito.verify(service).processReport(items);
    }

    @Test
    public void testReceivingInvalidMessage(){
        // given
        String message = TestData.getRandomJsonString();

        // when
        Executable listen = () -> consumer.listenReport(message);

        // then
        assertThrows(RuntimeException.class, listen);
    }

    @Test
    public void testReadingValueWithObjectMapper() throws JsonProcessingException {
        // given
        List<ChangeSetItem> items = TestData.getChangeSet();
        String message = TestData.getChangeSetAsJson();

        // when
        List<ChangeSetItem> listFromMessage = mapper.readValue(message, new TypeReference<List<ChangeSetItem>>() {});

        // then
        assertThat(listFromMessage).isEqualTo(items);
    }

}