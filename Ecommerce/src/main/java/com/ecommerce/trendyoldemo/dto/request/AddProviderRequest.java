package com.ecommerce.trendyoldemo.dto.request;

import com.ecommerce.trendyoldemo.entity.ProductEntity;
import lombok.Data;

import java.util.List;

@Data
public class AddProviderRequest {

    private String password;
    private String email;

    private String shopName;
    private String picUrl;
    private List<ProductEntity> products;

}
