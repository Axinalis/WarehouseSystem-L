package com.axinalis.store;

import com.axinalis.store.consumer.KafkaConsumer;
import com.axinalis.store.data.FullProductId;
import com.axinalis.store.data.ProductEntity;
import com.axinalis.store.data.StoreRepository;
import com.axinalis.store.service.impl.StoreServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.junit.jupiter.api.function.Executable;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {KafkaConsumer.class, StoreServiceImpl.class, ObjectMapper.class})
public class ReceivingMessageFromKafkaIntegrationTest {

    @MockBean
    private StoreRepository repository;

    @Autowired
    private KafkaConsumer consumer;

    @Test
    public void testSendingRequest(){
        // given
        String requestToConsumer = TestData.getChangeSetAsJson();
        List<ProductEntity> entities = TestData.getOutOfStockEntities();
        List<ProductEntity> updatedEntities = TestData.getEntities();
        List<FullProductId> ids = TestData.getFullIds();
        for (int i = 0; i < 4; i++) {
            when(repository.findById(ids.get(i)))
                    .thenReturn(Optional.of(entities.get(i)));
        }

        // when
        consumer.listenResponse(requestToConsumer);

        // then
        Mockito.verify(repository, times(4)).findById(any());
        // Here we're making sure that repository will save correct objects
        Mockito.verify(repository).save(updatedEntities.get(0));
        Mockito.verify(repository).save(updatedEntities.get(1));
        Mockito.verify(repository).save(updatedEntities.get(2));
        Mockito.verify(repository).save(updatedEntities.get(3));
    }

    @Test
    public void testInvalidString(){
        // given
        String invalidString = "Some random string with almost random words";

        // when
        Executable listen = () -> consumer.listenResponse(invalidString);

        // then
        assertThrows(RuntimeException.class, listen);
    }

    @Test
    public void testInvalidJsonString(){
        // given
        String simpleJsonString = TestData.getRandomJsonString();

        // when
        Executable listen = () -> consumer.listenResponse(simpleJsonString);

        // then
        assertThrows(RuntimeException.class, listen);
    }

}
