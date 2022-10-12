package com.axinalis.warehouse.service.impl;

import com.axinalis.warehouse.consumer.ChangeSetItem;
import com.axinalis.warehouse.sender.ResponseSender;
import com.axinalis.warehouse.repository.StoreItemRepository;
import com.axinalis.warehouse.service.ReportsService;
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
public class ReportsServiceImpl implements ReportsService {

    private static Logger log = LoggerFactory.getLogger(ReportsServiceImpl.class);

    private StoreItemsPublisher publisher;
    private StoreItemRepository repository;
    private ResponseSender sender;

    public ReportsServiceImpl(@Autowired StoreItemsPublisher publisher,
                              @Autowired StoreItemRepository repository,
                              @Autowired ResponseSender sender) {
        this.publisher = publisher;
        this.repository = repository;
        this.sender = sender;
    }

    @PostConstruct
    public void setupSink(){
        ConnectableFlux<List<ChangeSetItem>> flux = Flux.create(publisher).publish();

        for(int i = 0; i < 9; i++){
            flux.subscribe(new StoreItemsSub("Sub " + (i + 1), i + 1L, repository, this));
        }

        flux.connect();

        log.info("Initialization is complete");
    }

    @Override
    public void processReport(List<ChangeSetItem> items){
        publisher.publishItems(items);
    }

    @Override
    public void processResponse(List<ChangeSetItem> items){
        sender.sendTruckWithGoods(items);
    }

}
