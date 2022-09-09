package com.axinalis.store.data;

import java.util.Objects;

public class CategoryItem {

    private Long categoryId;
    private String categoryName;
    private Integer buyingFrequency;

    public CategoryItem() {
    }

    public CategoryItem(Long categoryId, String categoryName, Integer buyingFrequency) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.buyingFrequency = buyingFrequency;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getBuyingFrequency() {
        return buyingFrequency;
    }

    public void setBuyingFrequency(Integer buyingFrequency) {
        this.buyingFrequency = buyingFrequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryItem that = (CategoryItem) o;
        return Objects.equals(categoryId, that.categoryId) && Objects.equals(categoryName, that.categoryName) && Objects.equals(buyingFrequency, that.buyingFrequency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, buyingFrequency);
    }

    @Override
    public String toString() {
        return "CategoryItem{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", buyingFrequency=" + buyingFrequency +
                '}';
    }
}
