package com.axinalis.store.runner;

import com.axinalis.store.changer.DataChanger;
import com.axinalis.store.data.Database;
import com.axinalis.store.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MainRunner {

    private static Logger log = LoggerFactory.getLogger(MainRunner.class);
    private static AtomicBoolean mainCycle = new AtomicBoolean(true);
    @Value("${sleep.time}")
    private Long sleepTime;
    @Autowired
    private KafkaSender sender;
    @Autowired
    private DataChanger changer;
    @Autowired
    private Database database;

    @EventListener(ApplicationReadyEvent.class)
    public void appMainCycle(){
        log.debug("Main cycle is started");
        while(mainCycle.get()){
            try{
                Thread.sleep(sleepTime / database.getNumberOfStores());
            } catch(InterruptedException e){
                log.error("Some error occurred during running main cycle: {}", e.getMessage());
                stopMainCycle();
            }
            log.debug("Sending new report to warehouse");
            sender.sendNewReport(changer.changeData());
            log.debug("The report to warehouse was sent");
        }
        log.debug("Main cycle has ended");
    }

    public static void stopMainCycle(){
        mainCycle.set(false);
    }
}
