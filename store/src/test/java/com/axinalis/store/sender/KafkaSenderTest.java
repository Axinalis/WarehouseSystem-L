package com.axinalis.store.sender;

import com.axinalis.store.TestData;
import com.axinalis.store.changer.ChangeSetItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class KafkaSenderTest {

    private static KafkaSender sender;
    private static KafkaTemplate<String, String> template;
    private static ArgumentCaptor<String> captor;
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup(){
        mapper = new ObjectMapper();
        template = Mockito.mock(KafkaTemplate.class);
        sender = new KafkaSender(template, mapper);
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
        List<ChangeSetItem> items = TestData.getChangeSet();
        when(template.send(any(), captor.capture())).thenReturn(null);
        String expectedJson = TestData.getChangeSetAsJson();

        // when
        sender.sendNewReport(items);

        // then
        assertThat(captor.getValue()).isEqualTo(expectedJson);
    }

    @Test
    public void testObjectMapper() throws JsonProcessingException {
        // given
        mapper = new ObjectMapper();
        List<ChangeSetItem> items = TestData.getChangeSet();
        String expectedJson = TestData.getChangeSetAsJson();

        // when
        String json = mapper.writeValueAsString(items);

        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void testSendingInvalidData() {
        // when
        Executable sending = () -> sender.sendNewReport(null);

        // then
        assertThrows(RuntimeException.class, sending);
    }
}