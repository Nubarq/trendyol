package com.ecommerce.trendyoldemo.dto.request;

import lombok.Data;

@Data
public class UpdateCustomerRequest {

    private String name;
    private String surname;
    private String phone;
}
