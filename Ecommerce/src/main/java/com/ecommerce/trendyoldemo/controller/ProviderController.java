package com.ecommerce.trendyoldemo.controller;


import com.ecommerce.trendyoldemo.dto.request.AddProviderRequest;
import com.ecommerce.trendyoldemo.dto.request.ProviderRequest;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.dto.response.ProviderResponse;
import com.ecommerce.trendyoldemo.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@MyRestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService service;

    @PostMapping("/upgradeToProvider")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public MessageResponse upgradeToProvider(@RequestBody AddProviderRequest request) {
        return service.upgradeToProvider(request);
    }

    @GetMapping("/getAll")
    public Page<ProviderResponse> getAllProviders(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {

        return service.getAllProviders(page, size);
    }

    @GetMapping("/getById")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProviderResponse getProviderById(@RequestParam Long id){
        return service.getProviderById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_PROVIDER')")
    public ProviderResponse updateProvider(@RequestBody ProviderRequest request){
        return service.updateProvider(request);
    }

    }
