package com.axinalis.warehouse.repository;

import com.axinalis.warehouse.entity.FullProductId;
import com.axinalis.warehouse.entity.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, FullProductId> {

    @Modifying
    @Query(value = "UPDATE Products p SET p.current_stock=:newCurrentStock " +
            "WHERE p.store_id=:storeId AND p.category_id=:categoryId AND p.product_id=:productId", nativeQuery = true)
    void updateCurrentStock(@Param("storeId") Long storeId,
                            @Param("categoryId")Long categoryId,
                            @Param("productId") Long productId,
                            @Param("newCurrentStock") Long newCurrentStock);

}
