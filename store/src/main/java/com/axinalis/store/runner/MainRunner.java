package com.axinalis.store.runner;

import com.axinalis.store.changer.ChangeSetItem;
import com.axinalis.store.sender.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MainRunner {

    private static Logger log = LoggerFactory.getLogger(MainRunner.class);
    private static AtomicBoolean mainCycle = new AtomicBoolean(true);
    @Value("${sleep.time}")
    private Long sleepTime;
    private KafkaSender sender;

    public MainRunner() {
    }

    public MainRunner(@Autowired KafkaSender sender) {
        this.sender = sender;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appMainCycle(){
        List<ChangeSetItem> item = List.of(new ChangeSetItem(1L, 1L, 1L, 2L));
        log.debug("Main cycle is started");
        while(mainCycle.get()){
            try{
                Thread.sleep(sleepTime);
            } catch(InterruptedException e){
                log.error("Some error occurred during running main cycle: {}", e.getMessage());
                stopMainCycle();
            }
            sender.sendNewReport(item);
        }
        log.debug("Main cycle has ended");
    }

    public static void stopMainCycle(){
        mainCycle.set(false);
    }
}
