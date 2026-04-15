package com.ecommerce.trendyoldemo.dto.request;

import lombok.Data;

@Data
public class CustomerRequest {
    private String name;
    private String surname;
    private String phone;
    private String password;
}
