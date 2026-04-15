package com.ecommerce.trendyoldemo.service;


import com.ecommerce.trendyoldemo.dto.request.AddProviderRequest;
import com.ecommerce.trendyoldemo.dto.request.ProviderRequest;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.dto.response.ProviderResponse;
import com.ecommerce.trendyoldemo.entity.ProviderEntity;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.mapper.ProviderMapper;
import com.ecommerce.trendyoldemo.repository.CustomerRepository;
import com.ecommerce.trendyoldemo.repository.ProviderRepository;
import com.ecommerce.trendyoldemo.repository.RoleRepository;
import com.ecommerce.trendyoldemo.repository.UserRepository;
import com.ecommerce.trendyoldemo.utility.JwtUtil;
import com.ecommerce.trendyoldemo.utility.RefreshTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationService verificationService;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenUtil refreshTokenUtil;


    public MessageResponse upgradeToProvider(AddProviderRequest request) {
        UserEntity user = userService.getCurrentUser();

        if (providerRepository.findByUserId(user.getId()).isPresent()) {
            throw new CustomException("Artıq provider qeydiyyatı var", "Provider already exists", "Conflict", 409, null);
        }

        ProviderEntity provider = new ProviderEntity();
        provider.setUser(user);
        provider.setShopName(request.getShopName());
        provider.setPicUrl(request.getPicUrl());

        providerRepository.save(provider);

        roleRepository.assignProviderRoles(user.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("İstifadəçi provider olaraq yeniləndi!");
        return response;
    }

    public Page<ProviderResponse> getAllProviders(int page, int size) {
        int maxSize = 10;
        if(size > maxSize)
            size = maxSize;
        Pageable pageable = PageRequest.of(page, size);
        Page<ProviderEntity> providerPage = providerRepository.findAll(pageable);
        return providerPage.map(ProviderMapper::toDTO);
    }


    public ProviderResponse getProviderById(Long id){
        ProviderEntity provider=providerRepository.findById(id).orElseThrow(() -> new CustomException(
                "Bu id ile Company tapilmadi"
                , "Company does not exist", "Conflict"
                , 409, null));
        return ProviderMapper.toDTO(provider);
    }

    public ProviderResponse updateProvider(ProviderRequest request){
        UserEntity user = userService.getCurrentUser();
        ProviderEntity provider = providerRepository.findByUserId(user.getId()).orElseThrow(() -> new CustomException(
                "Bu id ile Company tapilmadi"
                , "Company does not exist", "Conflict"
                , 409, null));

        if (request.getPicUrl() != null) {
            provider.setPicUrl(request.getPicUrl());
        }
        if (request.getShopName() != null) {
            provider.setShopName(request.getShopName());
        }
        providerRepository.save(provider);
        return ProviderMapper.toDTO(provider);
    }



}
