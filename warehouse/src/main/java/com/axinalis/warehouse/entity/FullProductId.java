package com.axinalis.warehouse.entity;

import java.io.Serializable;
import java.util.Objects;

public class FullProductId implements Serializable {

    private Long storeId;
    private Long productId;
    private Long categoryId;

    public FullProductId() {
    }

    public FullProductId(Long storeId, Long productId, Long categoryId) {
        this.storeId = storeId;
        this.productId = productId;
        this.categoryId = categoryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullProductId that = (FullProductId) o;
        return Objects.equals(storeId, that.storeId) && Objects.equals(productId, that.productId) && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId, categoryId);
    }

    @Override
    public String toString() {
        return "FullProductId{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", categoryId=" + categoryId +
                '}';
    }
}
