package com.axinalis.warehouse.entity;

import java.io.Serializable;
import java.util.Objects;

public class FullProductId implements Serializable {

    private Long storeId;
    private Long productId;

    public FullProductId() {
    }

    public FullProductId(Long storeId, Long productId) {
        this.storeId = storeId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullProductId that = (FullProductId) o;
        return Objects.equals(storeId, that.storeId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId);
    }
}
