package com.ecommerce.trendyoldemo.dto.request;

import lombok.Data;

@Data
public class AddCustomerRequest {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
}
