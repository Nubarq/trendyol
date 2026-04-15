package com.ecommerce.trendyoldemo.mapper;

import com.ecommerce.trendyoldemo.dto.response.OrderItemResponse;
import com.ecommerce.trendyoldemo.dto.response.ProductResponse;
import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.entity.OrderItemEntity;
import com.ecommerce.trendyoldemo.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {
    public static OrderItemResponse toDTO(OrderItemEntity orderItem) {
        OrderItemResponse response= new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setStatus(orderItem.getStatus());
        response.setQuantity(orderItem.getQuantity());
        response.setOrderId(orderItem.getOrder().getId());
        response.setProductId(orderItem.getProduct().getId());
        response.setUnitPrice(orderItem.getUnitPrice());

        return response;
    }

    public static List<ProductResponse> toDTOList(List<ProductEntity> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
