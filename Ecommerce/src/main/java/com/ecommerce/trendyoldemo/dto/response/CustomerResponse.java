package com.ecommerce.trendyoldemo.dto.response;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;

}
