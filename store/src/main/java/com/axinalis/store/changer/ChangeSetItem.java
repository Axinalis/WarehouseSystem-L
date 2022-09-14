package com.axinalis.store.changer;

import java.util.Objects;

public class ChangeSetItem {

    private Long storeId;
    private Long categoryId;
    private Long productId;
    private Long currentStock;

    public ChangeSetItem() {
    }

    public ChangeSetItem(Long storeId, Long categoryId, Long productId, Long currentStock) {
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.productId = productId;
        this.currentStock = currentStock;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        ChangeSetItem that = (ChangeSetItem) o;
        return Objects.equals(storeId, that.storeId) && Objects.equals(categoryId, that.categoryId) && Objects.equals(productId, that.productId) && Objects.equals(currentStock, that.currentStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, categoryId, productId, currentStock);
    }

    @Override
    public String toString() {
        return "ChangeSetItem{" +
                "storeId=" + storeId +
                ", categoryId=" + categoryId +
                ", productId=" + productId +
                ", stockChange=" + currentStock +
                '}';
    }
}
