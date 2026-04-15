package com.ecommerce.trendyoldemo.dto.request;

import com.ecommerce.trendyoldemo.entity.Pictures;
import lombok.Data;

import java.util.List;

@Data
public class AddProductRequest {

    private String name;
    private String description;
    private Double price;

    private List<Pictures> pictures;
}
