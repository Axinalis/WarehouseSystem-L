package com.axinalis.warehouse.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "products")
@IdClass(FullProductId.class)
public class StoreItemEntity {

    @Id
    private Long storeId;
    @Id
    private Long productId;
    @Id
    private Long categoryId;
    private Long maxStock;
    private Long currentStock;

    public StoreItemEntity() {
    }

    public StoreItemEntity(Long storeId, Long productId, Long categoryId, Long maxStock, Long currentStock) {
        this.storeId = storeId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.maxStock = maxStock;
        this.currentStock = currentStock;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Long maxStock) {
        this.maxStock = maxStock;
    }

    public Long getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Long currentStock) {
        this.currentStock = currentStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItemEntity entity = (StoreItemEntity) o;
        return Objects.equals(storeId, entity.storeId) && Objects.equals(productId, entity.productId) && Objects.equals(categoryId, entity.categoryId) && Objects.equals(maxStock, entity.maxStock) && Objects.equals(currentStock, entity.currentStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId, categoryId, maxStock, currentStock);
    }

    @Override
    public String toString() {
        return "StoreItemEntity{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                ", maxStock=" + maxStock +
                ", currentStock=" + currentStock +
                '}';
    }
}
