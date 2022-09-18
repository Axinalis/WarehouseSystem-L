package com.axinalis.warehouse.repository;

import com.axinalis.warehouse.entity.FullProductId;
import com.axinalis.warehouse.entity.StoreItemEntity;
import org.apache.catalina.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItemEntity, FullProductId> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Products SET current_stock=:newCurrentStock " +
            "WHERE store_id=:storeId AND category_id=:categoryId AND product_id=:productId", nativeQuery = true)
    void updateCurrentStock(@Param("storeId") Long storeId,
                            @Param("categoryId") Long categoryId,
                            @Param("productId") Long productId,
                            @Param("newCurrentStock") Long newCurrentStock);

    @Query(value = "SELECT category_id FROM Products WHERE store_id=:storeId AND current_stock/max_stock<0.3", nativeQuery = true)
    List<Long> searchOutOfStockCategories(@Param("storeId") Long storeId);

    @Query(value = "SELECT * FROM Products WHERE store_id=:storeId AND category_id=:categoryId", nativeQuery = true)
    List<StoreItemEntity> getItemsByCategory(@Param("storeId") Long storeId, @Param("categoryId") Long categoryId);

    @Transactional
    @Query(value = "UPDATE Products SET current_stock=max_stock WHERE store_id=:storeId AND category_id=:categoryId", nativeQuery = true)
    void updateCategoryStocks(@Param("storeId") Long storeId, @Param("categoryId") Long categoryId);
}
