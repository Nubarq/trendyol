package com.ecommerce.trendyoldemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private long id;
    private Long productId;   // optional
    private String text; // from CommentRequest
    private Integer stars;      // from RatingRequest

    private String reviewerEmail;

}
