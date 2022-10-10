package com.axinalis.store.service;

import com.axinalis.store.changer.ChangeSetItem;

import java.util.List;

public interface StoreService {

    void addToStocks(List<ChangeSetItem> items);

}
