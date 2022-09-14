package com.axinalis.store.data;

import java.util.Objects;

public class StoreItem {

    private Long itemId;
    private String title;
    private Long categoryId;
    private Long maxStock;
    private Long currentStock;

    public StoreItem() {
    }

    public StoreItem(Long itemId, String title, Long categoryId, Long maxStock, Long currentStock) {
        this.itemId = itemId;
        this.title = title;
        this.categoryId = categoryId;
        this.maxStock = maxStock;
        this.currentStock = currentStock;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        if(currentStock <= 0){
            throw new RuntimeException("Product \"" + this.title + "\" has ran out of stock");
        } else {
            this.currentStock = currentStock;
        }
    }

    public void decreaseCurrentStockBy(Integer decreasingAmount){
        if(this.currentStock - decreasingAmount <= 0){
            throw new RuntimeException("Product \"" + this.title + "\" has ran out of stock");
        } else {
            this.currentStock -= decreasingAmount;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreItem item = (StoreItem) o;
        return Objects.equals(itemId, item.itemId) && Objects.equals(title, item.title) && Objects.equals(categoryId, item.categoryId) && Objects.equals(maxStock, item.maxStock) && Objects.equals(currentStock, item.currentStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, title, categoryId, maxStock, currentStock);
    }

    @Override
    public String toString() {
        return "StoreItem{" +
                "itemId=" + itemId +
                ", title='" + title + '\'' +
                ", categoryId=" + categoryId +
                ", maxStock=" + maxStock +
                ", currentStock=" + currentStock +
                '}';
    }
}
