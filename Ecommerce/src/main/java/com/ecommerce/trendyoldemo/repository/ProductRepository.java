package com.ecommerce.trendyoldemo.repository;

import com.ecommerce.trendyoldemo.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByProviderId(Long providerId, Pageable pageable);
//    Optional<ProductEntity> findByUserId(Long userId);


}
