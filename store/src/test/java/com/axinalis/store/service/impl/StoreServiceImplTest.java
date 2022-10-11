package com.axinalis.store.service.impl;

import com.axinalis.store.TestData;
import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.FullProductId;
import com.axinalis.store.data.ProductEntity;
import com.axinalis.store.data.StoreRepository;
import com.axinalis.store.service.StoreService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StoreServiceImplTest {

    private static StoreService service;

    private static StoreRepository repository;

    @BeforeAll
    public static void setup(){
        repository = Mockito.mock(StoreRepository.class);
        service = new StoreServiceImpl(repository);
    }

    @BeforeEach
    public void resetMocks(){
        Mockito.reset(repository);
    }

    @Test
    public void testRefillingStocks(){
        // given
        List<FullProductId> ids = TestData.getFullIds();
        List<ProductEntity> entities = TestData.getEntities();
        List<ChangeSetItem> items = TestData.getChangeSet();
        for(int i = 0; i < 4; i++){
            when(repository.findById(ids.get(i)))
                    .thenReturn(Optional.of(entities.get(i)));
        }

        // when
        service.addToStocks(items);

        // then
        Mockito.verify(repository, times(items.size())).findById(any());
        Mockito.verify(repository, times(items.size())).save(any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void testInvalidData(){
        // given
        List<ChangeSetItem> items = TestData.getChangeSet();
        when(repository.findById(any()))
                .thenReturn(Optional.empty());

        // when
        service.addToStocks(items);

        // then
        Mockito.verify(repository, times(items.size())).findById(any());
        Mockito.verify(repository, never()).save(any());
        Mockito.verifyNoMoreInteractions(repository);
    }
}