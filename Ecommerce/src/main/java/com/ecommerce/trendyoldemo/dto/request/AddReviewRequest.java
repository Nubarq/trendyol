package com.ecommerce.trendyoldemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReviewRequest {
    private Long targetId;          // serviceId or productId depending on type
    private String commentText; // from CommentRequest
    private Integer stars;      // from RatingRequest
}
