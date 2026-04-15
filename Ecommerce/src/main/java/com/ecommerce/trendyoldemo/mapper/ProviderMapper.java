package com.ecommerce.trendyoldemo.mapper;


import com.ecommerce.trendyoldemo.dto.response.ProviderResponse;
import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.entity.ProviderEntity;
import com.ecommerce.trendyoldemo.entity.ReviewEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProviderMapper {
    public static ProviderResponse toDTO(ProviderEntity entity) {
        ProviderResponse response = new ProviderResponse();
        response.setId(entity.getId());
        response.setAverageRateing(entity.getAverageRating());
        response.setShopName(entity.getShopName());
        response.setPicUrl(entity.getPicUrl());

        return response;
    }

    public static List<ReviewResponse> toDTOList(List<ReviewEntity> entities) {
        return entities.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }
}
