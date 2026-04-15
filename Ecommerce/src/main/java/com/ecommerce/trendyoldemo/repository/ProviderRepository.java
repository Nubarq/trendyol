package com.ecommerce.trendyoldemo.repository;


import com.ecommerce.trendyoldemo.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {
    Optional<ProviderEntity> findByUserId(Long userId);

}
