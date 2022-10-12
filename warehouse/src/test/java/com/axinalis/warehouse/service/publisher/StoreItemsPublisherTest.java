package com.axinalis.warehouse.service.publisher;

import com.axinalis.warehouse.TestData;
import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.axinalis.warehouse.repository.StoreItemRepository;
import com.axinalis.warehouse.service.ReportsService;
import com.axinalis.warehouse.service.subscriber.StoreItemsSub;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class StoreItemsPublisherTest {

    private static StoreItemsPublisher publisher;

    @BeforeAll
    public static void setup(){
        publisher = new StoreItemsPublisher();
    }

    @Test
    public void testPublishing(){
        // given
        StoreItemsSub sub = Mockito.mock(StoreItemsSub.class);
        Flux.create(publisher).subscribe(sub);
        List<ChangeSetItem> items = TestData.getChangeSet();

        // when
        publisher.publishItems(items);

        // then
        Mockito.verify(sub).onSubscribe(any(Subscription.class));
        Mockito.verify(sub).onNext(items);
        Mockito.verifyNoMoreInteractions(sub);
    }
}