package com.ecommerce.trendyoldemo.mapper;



import com.ecommerce.trendyoldemo.dto.response.ProductResponse;
import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductResponse toDTO(ProductEntity product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setAverageRating(product.getAverageRating());
        response.setProviderId(product.getProvider().getId());


        //  map reviews
        if (product.getReviews() != null && !product.getReviews().isEmpty()) {
            List<ReviewResponse> reviewResponses = product.getReviews().stream()
                    .map(review -> {
                        ReviewResponse c = new ReviewResponse();
                        c.setId(review.getId());
                        c.setText(review.getText());
                        c.setStars(review.getStars());
                        if (review.getUser() != null) {
                            c.setReviewerEmail(review.getUser().getEmail());
                        }
                        return c;
                    })
                    .toList();
            response.setReviews(reviewResponses);
        }

        return response;
    }

    public static List<ProductResponse> toDTOList(List<ProductEntity> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
