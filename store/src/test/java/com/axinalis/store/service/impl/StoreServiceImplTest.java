package com.axinalis.store.service.impl;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.data.FullProductId;
import com.axinalis.store.data.ProductEntity;
import com.axinalis.store.data.StoreRepository;
import com.axinalis.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class StoreServiceImplTest {

    private StoreService service;

    private StoreRepository repository;

    @BeforeEach
    public void setup(){
        repository = Mockito.mock(StoreRepository.class);
        service = new StoreServiceImpl(repository);
    }

    @Test
    public void testRefillingStocks(){
        // given
        List<ChangeSetItem> items = getChangeSet();
        List<ProductEntity> entities = getEntities();
        List<FullProductId> ids = getFullIds();
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
    public void test(){
        // given
        List<ChangeSetItem> items = getChangeSet();
        for(int i = 0; i < 4; i++){
            when(repository.findById(any()))
                    .thenReturn(Optional.empty());
        }

        // when
        service.addToStocks(items);

        // then
        Mockito.verify(repository, times(items.size())).findById(any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    private List<ChangeSetItem> getChangeSet(){
        List<ChangeSetItem> items = new ArrayList<>();
        items.add(new ChangeSetItem(1L, 1L, 12L, 50L));
        items.add(new ChangeSetItem(1L, 1L, 16L, 5L));
        items.add(new ChangeSetItem(1L, 1L, 19L, 15L));
        items.add(new ChangeSetItem(1L, 1L, 23L, 20L));
        return items;
    }

    private List<ProductEntity> getEntities(){
        List<ProductEntity> entities = new ArrayList<>();
        entities.add(new ProductEntity(1L, 1L, 12L, 100L, 99L, "some product 1"));
        entities.add(new ProductEntity(1L, 1L, 16L, 10L, 10L, "some product 2"));
        entities.add(new ProductEntity(1L, 1L, 19L, 30L, 21L, "some product 3"));
        entities.add(new ProductEntity(1L, 1L, 23L, 50L, 23L, "some product 4"));
        return entities;
    }

    private List<FullProductId> getFullIds(){
        List<FullProductId> ids = new ArrayList<>();
        ids.add(new FullProductId(1L, 1L, 12L));
        ids.add(new FullProductId(1L, 1L, 16L));
        ids.add(new FullProductId(1L, 1L, 19L));
        ids.add(new FullProductId(1L, 1L, 23L));
        return ids;
    }
}