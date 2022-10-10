package com.axinalis.store.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<ProductEntity, FullProductId> {



}
