package com.ecommerce.trendyoldemo.repository;


import com.ecommerce.trendyoldemo.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
//    List<ReviewEntity> findByCompanyServiceId(Long serviceId);
//    List<ReviewEntity> findByProviderServiceId(Long serviceId);

    List<ReviewEntity> findByProductId(Long productId);
//    boolean existsByUserIdAndCompanyServiceId(Long userId, Long serviceId);
//    boolean existsByUserIdAndProviderServiceId(Long userId, Long serviceId);

    boolean existsByUserIdAndProductId(Long userId, Long productId);

    
}
