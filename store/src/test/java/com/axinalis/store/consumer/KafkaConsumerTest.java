package com.axinalis.store.consumer;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class KafkaConsumerTest {

    private StoreService service;
    private KafkaConsumer consumer;
    private ObjectMapper mapper = new ObjectMapper();
    private ArgumentCaptor<List<ChangeSetItem>> captor;

    @BeforeEach
    public void setup(){
        service = Mockito.mock(StoreService.class);
        consumer = new KafkaConsumer(mapper, service);
        captor = ArgumentCaptor.forClass(List.class);
    }

    @Test
    public void testReceivingValidInfo() throws JsonProcessingException{
        // given
        List<ChangeSetItem> itemsSet = new ArrayList<>();
        itemsSet.add(new ChangeSetItem(1L, 2L, 23L, 199L));
        itemsSet.add(new ChangeSetItem(1L, 2L, 45L, 25L));
        itemsSet.add(new ChangeSetItem(1L, 3L, 49L, 87L));
        itemsSet.add(new ChangeSetItem(2L, 2L, 19L, 39L));
        String stringWithItems = mapper.writeValueAsString(itemsSet);

        // when
        consumer.listenResponse(stringWithItems);

        // then
        Mockito.verify(service).addToStocks(captor.capture());
        List<ChangeSetItem> result = captor.getValue();
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(itemsSet, result);
    }

    @Test
    public void testReceivingBrokenInfo() throws JsonProcessingException{
        // given
        String brokenString = "{\"id\":28, \"name\":\"Anton\"}";

        // when
        Executable ex = () -> consumer.listenResponse(brokenString);

        // then
        Assertions.assertThrows(RuntimeException.class, ex);
    }

}
