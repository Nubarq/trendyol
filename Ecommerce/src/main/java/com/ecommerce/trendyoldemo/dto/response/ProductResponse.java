package com.ecommerce.trendyoldemo.dto.response;

import com.ecommerce.trendyoldemo.entity.Pictures;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Double averageRating;
    private Long providerId;
    private List<ReviewResponse> reviews;


//    private List<Pictures> pictures;
}
