package com.ecommerce.trendyoldemo.dto.response;

import com.ecommerce.trendyoldemo.entity.ProductEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProviderResponse {
    private Long id;

    private String shopName;
    private String picUrl;
    private double averageRateing;
//    private List<ProductEntity> products;
}
