package com.axinalis.warehouse.service.publisher;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.function.Consumer;

@Component
public class StoreItemsPublisher implements Consumer<FluxSink<List<ChangeSetItem>>> {

    private FluxSink<List<ChangeSetItem>> sink;

    @Override
    public void accept(FluxSink<List<ChangeSetItem>> objectFluxSink) {
        this.sink = objectFluxSink;
    }

    public void publishItems(List<ChangeSetItem> items){
        sink.next(items);
    }

}
