package com.axinalis.warehouse.service;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.axinalis.warehouse.sender.ResponseSender;
import com.axinalis.warehouse.repository.StoreItemRepository;
import com.axinalis.warehouse.service.publisher.StoreItemsPublisher;
import com.axinalis.warehouse.service.subscriber.StoreItemsSub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ReportsService {

    private static Logger log = LoggerFactory.getLogger(ReportsService.class);

    private StoreItemsPublisher publisher;
    @Autowired
    private StoreItemRepository repository;
    @Autowired
    private ResponseSender sender;

    @PostConstruct
    public void setupSink(){
        this.publisher = new StoreItemsPublisher();
        ConnectableFlux<List<ChangeSetItem>> flux = Flux.create(this.publisher).publish();

        for(int i = 0; i < 9; i++){
            flux.subscribe(new StoreItemsSub("Sub " + (i + 1), repository, i + 1L, this));
        }

        flux.connect();

        log.info("Initialization is complete");
    }

    public void processReport(List<ChangeSetItem> items){
        publisher.publishItems(items);
    }

    public void processResponse(List<ChangeSetItem> items){
        sender.sendTruckWithGoods(items);
    }

}
