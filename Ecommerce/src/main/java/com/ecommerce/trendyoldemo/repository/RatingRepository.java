package com.ecommerce.trendyoldemo.repository;

import com.ecommerce.trendyoldemo.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository  extends JpaRepository<RatingEntity, Long> {
}
