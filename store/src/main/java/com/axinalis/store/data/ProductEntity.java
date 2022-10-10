package com.axinalis.store.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "products")
@IdClass(FullProductId.class)
public class ProductEntity {

    @Id
    private Long storeId;
    @Id
    private Long categoryId;
    @Id
    private Long productId;
    private Long maxStock;
    private Long currentStock;
    private String title;

    public ProductEntity() {
    }

    public ProductEntity(Long storeId, Long categoryId, Long productId, Long maxStock, Long currentStock, String title) {
        this.productId = productId;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.maxStock = maxStock;
        this.currentStock = currentStock;
        this.title = title;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(productId, that.productId) && Objects.equals(storeId, that.storeId) && Objects.equals(categoryId, that.categoryId) && Objects.equals(maxStock, that.maxStock) && Objects.equals(currentStock, that.currentStock) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, storeId, categoryId, maxStock, currentStock, title);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", storeId=" + storeId +
                ", categoryId=" + categoryId +
                ", maxStock=" + maxStock +
                ", currentStock=" + currentStock +
                ", title='" + title + '\'' +
                '}';
    }
}
