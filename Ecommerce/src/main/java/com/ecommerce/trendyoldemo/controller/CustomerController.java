package com.ecommerce.trendyoldemo.controller;



import com.ecommerce.trendyoldemo.dto.request.AddCustomerRequest;
import com.ecommerce.trendyoldemo.dto.request.CustomerRequest;
import com.ecommerce.trendyoldemo.dto.request.VerifyAccountRequest;
import com.ecommerce.trendyoldemo.dto.response.CustomerResponse;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.service.CustomerService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@MyRestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public MessageResponse register(@RequestBody AddCustomerRequest request) throws MessagingException {
        return customerService.register(request);
    }

    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestBody VerifyAccountRequest request) {
        return customerService.verifyAccount(request.getEmail(), request.getToken());
    }

    @GetMapping("/getById")
    public CustomerResponse getCustomerById(@RequestParam Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getALl")
    public Page<CustomerResponse> getALlCustomers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size){
        return customerService.getALlCustomers(page, size);
    }

    @PutMapping("/update")
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest request){
        return customerService.updateCustomer(request);
    }


    }
