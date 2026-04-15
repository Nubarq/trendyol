package com.ecommerce.trendyoldemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private Double unitPrice;
    private boolean status;
    private Long productId;
    private Long orderId;
}
