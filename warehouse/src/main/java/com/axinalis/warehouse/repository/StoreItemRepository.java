package com.axinalis.warehouse.repository;

import com.axinalis.warehouse.entity.FullProductId;
import com.axinalis.warehouse.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, FullProductId> {
}
