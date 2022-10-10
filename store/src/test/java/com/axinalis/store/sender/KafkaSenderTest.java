package com.axinalis.store.sender;

import com.axinalis.store.changer.ChangeSetItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class KafkaSenderTest {

    private KafkaSender sender;
    private KafkaTemplate<String, String> template;
    private ArgumentCaptor<String> captor;

    @BeforeEach
    public void setup(){
        template = Mockito.mock(KafkaTemplate.class);
        sender = new KafkaSender(template, new ObjectMapper());
        captor = ArgumentCaptor.forClass(String.class);
    }

    @Test
    public void testSendingEmptyData(){
        // given
        when(template.send(any(), captor.capture())).thenReturn(null);

        // when
        sender.sendNewReport(new ArrayList<>());

        // then
        assertEquals("[]", captor.getValue());
    }

    @Test
    public void testSendingValidData(){
        // given
        List<ChangeSetItem> items = new ArrayList<>();
        items.add(new ChangeSetItem(1L, 1L, 1L, 30L));
        items.add(new ChangeSetItem(1L, 1L, 2L, 25L));
        items.add(new ChangeSetItem(1L, 1L, 3L, 20L));
        when(template.send(any(), captor.capture())).thenReturn(null);

        // when
        sender.sendNewReport(items);

        // then
        assertEquals("[{\"storeId\":1,\"categoryId\":1,\"productId\":1,\"currentStock\":30}," +
                                "{\"storeId\":1,\"categoryId\":1,\"productId\":2,\"currentStock\":25}," +
                                "{\"storeId\":1,\"categoryId\":1,\"productId\":3,\"currentStock\":20}]",
                                captor.getValue());
    }

    @Test
    public void testObjectMapper() throws Exception{
        // given
        ObjectMapper mapper = new ObjectMapper();
        List<ChangeSetItem> items = new ArrayList<>();
        items.add(new ChangeSetItem(1L, 1L, 1L, 30L));
        items.add(new ChangeSetItem(1L, 1L, 2L, 25L));
        items.add(new ChangeSetItem(1L, 1L, 3L, 20L));

        // when
        String json = mapper.writeValueAsString(items);

        // then
        assertEquals("[{\"storeId\":1,\"categoryId\":1,\"productId\":1,\"currentStock\":30}," +
                        "{\"storeId\":1,\"categoryId\":1,\"productId\":2,\"currentStock\":25}," +
                        "{\"storeId\":1,\"categoryId\":1,\"productId\":3,\"currentStock\":20}]", json);
    }

    @Test
    public void testSendingInvalidData() throws JsonProcessingException {
        // when
        Executable sending = () -> sender.sendNewReport(null);

        // then
        assertThrows(RuntimeException.class, sending);
    }
}