package com.ecommerce.trendyoldemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Long id;
    private int stars;
    private Long customerId;
    private String customerEmail;
}
