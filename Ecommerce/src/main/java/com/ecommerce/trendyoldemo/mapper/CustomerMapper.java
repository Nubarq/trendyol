package com.ecommerce.trendyoldemo.mapper;





import com.ecommerce.trendyoldemo.dto.response.CustomerResponse;
import com.ecommerce.trendyoldemo.entity.CustomerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {
    public static CustomerResponse toDTO(CustomerEntity customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setSurname(customer.getSurname());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getUser().getEmail());
        return response;
    }

    public static List<CustomerResponse> toDTOList(List<CustomerEntity> customers) {
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
