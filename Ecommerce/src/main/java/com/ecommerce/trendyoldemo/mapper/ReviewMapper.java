package com.ecommerce.trendyoldemo.mapper;



import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.entity.ReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {
    public static ReviewResponse toDTO(ReviewEntity entity) {
        ReviewResponse response = new ReviewResponse();
        response.setId(entity.getId());
        response.setStars(entity.getStars());
        response.setText(entity.getText());
        response.setReviewerEmail(entity.getUser().getEmail());

        return response;
    }

    public static List<ReviewResponse> toDTOList(List<ReviewEntity> entities) {
        return entities.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}
