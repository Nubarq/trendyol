package com.ecommerce.trendyoldemo.mapper;

import com.ecommerce.trendyoldemo.dto.response.OrderItemResponse;
import com.ecommerce.trendyoldemo.dto.response.OrderResponse;
import com.ecommerce.trendyoldemo.dto.response.ProductResponse;
import com.ecommerce.trendyoldemo.entity.OrderEntity;
import com.ecommerce.trendyoldemo.entity.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderResponse toDTO(OrderEntity order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalPrice(order.getTotalPrice());
        response.setCustomerId(order.getCustomer().getId());




        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream()
                    .map(orderItem -> {
                        OrderItemResponse c = new OrderItemResponse();
                        c.setId(orderItem.getId());
                        c.setOrderId(orderItem.getOrder().getId());
                        c.setQuantity(orderItem.getQuantity());
                        c.setStatus(orderItem.getStatus());
                        c.setProductId(orderItem.getProduct().getId());
                        c.setOrderId(orderItem.getOrder().getId());
                        return c;
                    })
                    .toList();
//            response.setOrderItems(orderItemResponses);
            response.setOrderItems(orderItemResponses);
        }

        return response;
    }

    public static List<ProductResponse> toDTOList(List<ProductEntity> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }
}
