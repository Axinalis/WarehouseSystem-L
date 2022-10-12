package com.axinalis.warehouse.service;

import com.axinalis.warehouse.consumer.ChangeSetItem;

import java.util.List;

public interface ReportsService {
    void processReport(List<ChangeSetItem> items);

    void processResponse(List<ChangeSetItem> toList);
}
