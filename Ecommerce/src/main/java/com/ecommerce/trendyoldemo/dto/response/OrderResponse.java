package com.ecommerce.trendyoldemo.dto.response;

import com.ecommerce.trendyoldemo.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long customerId;
    private Double totalPrice;
    private List<OrderItemResponse> orderItems;


}
