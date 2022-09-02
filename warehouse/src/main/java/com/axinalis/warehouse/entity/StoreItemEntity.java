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
    private Long maxStock;
    private Long currentStock;

    public StoreItemEntity() {
    }

    public StoreItemEntity(Long storeId, Long productId, Long maxStock, Long currentStock) {
        this.storeId = storeId;
        this.productId = productId;
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
        StoreItemEntity that = (StoreItemEntity) o;
        return Objects.equals(storeId, that.storeId) && Objects.equals(productId, that.productId) && Objects.equals(maxStock, that.maxStock) && Objects.equals(currentStock, that.currentStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId, maxStock, currentStock);
    }

    @Override
    public String toString() {
        return "StoreItemEntity{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", maxStock=" + maxStock +
                ", currentStock=" + currentStock +
                '}';
    }
}
