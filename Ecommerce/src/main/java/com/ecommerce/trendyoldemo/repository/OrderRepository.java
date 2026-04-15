package com.ecommerce.trendyoldemo.repository;

import com.ecommerce.trendyoldemo.entity.OrderEntity;
import com.ecommerce.trendyoldemo.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findByCustomerId(Long customerrId, Pageable pageable);
    Optional<OrderEntity> findByCustomerId(Long customerrId);

}
