package com.axinalis.store.consumer;

import com.axinalis.store.TestData;
import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KafkaConsumerTest {

    private static StoreService service;
    private static KafkaConsumer consumer;
    private static ObjectMapper mapper;
    private static ArgumentCaptor<List<ChangeSetItem>> captor;

    @BeforeAll
    public static void setup(){
        service = Mockito.mock(StoreService.class);
        mapper = new ObjectMapper();
        consumer = new KafkaConsumer(mapper, service);
        captor = ArgumentCaptor.forClass(List.class);
    }

    @Test
    public void testReceivingValidInfo() {
        // given
        List<ChangeSetItem> itemsSet = TestData.getChangeSet();
        String stringWithItems = TestData.getChangeSetAsJson();

        // when
        consumer.listenResponse(stringWithItems);

        // then
        Mockito.verify(service).addToStocks(captor.capture());
        List<ChangeSetItem> result = captor.getValue();
        assertThat(result.size()).isEqualTo(4);
        assertThat(result).isEqualTo(itemsSet);
    }

    @Test
    public void testReceivingBrokenInfo() {
        // given
        String brokenString = TestData.getRandomJsonString();

        // when
        Executable listen = () -> consumer.listenResponse(brokenString);

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
