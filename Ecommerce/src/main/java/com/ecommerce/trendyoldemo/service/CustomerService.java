package com.ecommerce.trendyoldemo.service;
import com.ecommerce.trendyoldemo.dto.request.AddCustomerRequest;
import com.ecommerce.trendyoldemo.dto.request.CustomerRequest;
import com.ecommerce.trendyoldemo.dto.response.AuthResponse;
import com.ecommerce.trendyoldemo.dto.response.CustomerResponse;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.entity.CustomerEntity;
import com.ecommerce.trendyoldemo.entity.OrderEntity;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.mapper.CustomerMapper;
import com.ecommerce.trendyoldemo.repository.CustomerRepository;
import com.ecommerce.trendyoldemo.repository.RoleRepository;
import com.ecommerce.trendyoldemo.repository.UserRepository;
import com.ecommerce.trendyoldemo.utility.JwtUtil;
import com.ecommerce.trendyoldemo.utility.RefreshTokenUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

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

    public MessageResponse register(AddCustomerRequest request) throws MessagingException {
        userService.isUserExists(request.getEmail());

        UserEntity user = new UserEntity(request.getEmail(), passwordEncoder.encode(request.getPassword()));
        user.setUserType(1);
        user.setVerified(false);

        userRepository.save(user);


        CustomerEntity customer = new CustomerEntity();
        OrderEntity order = new OrderEntity(customer);
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setPhone(request.getPhone());
        customer.setUser(user);
        customer.setOrder(order);

        customerRepository.save(customer);

        String token = verificationService.generateVerificationToken(user);
        verificationService.sendVerificationEmail(user.getEmail(), token);

        MessageResponse response = new MessageResponse();
        response.setMessage("E-poçtunuza təsdiq kodu göndərildi!");

        return response;
    }

    @Transactional
    public ResponseEntity<?> verifyAccount(String email, String token) {
        verificationService.verifyAccount(email, token);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("İstifadəçi tapılmadı", "User not found", "Not Found", 404, null));

        roleRepository.assignCustomerRoles(user.getId());

        // 🔥 EntityManager flush etmək və user-i yenidən oxumaq
        userRepository.flush();
        user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found after role assignment", "", "", 404, null));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);
        final String refreshToken = refreshTokenUtil.generateRefreshToken(userDetails);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        AuthResponse response = new AuthResponse(email, jwt, refreshToken, roles);
        return ResponseEntity.ok(response);
    }



    public CustomerResponse getCustomerById(Long id){
        CustomerEntity customer=customerRepository.findById(id).orElseThrow(() -> new CustomException(
                "Bu id ile Company tapilmadi"
                , "Company does not exist", "Conflict"
                , 409, null));
        return CustomerMapper.toDTO(customer);
    }

    public Page<CustomerResponse> getALlCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerEntity> customerPage = customerRepository.findAll( pageable);

        // map each entity to response
        return customerPage.map(CustomerMapper::toDTO);
    }

    public CustomerResponse updateCustomer(CustomerRequest request){
        UserEntity user = userService.getCurrentUser();
        CustomerEntity customer = customerRepository.findByUserId(user.getId()).orElseThrow(() -> new CustomException(
                "Bu id ile Company tapilmadi"
                , "Company does not exist", "Conflict"
                , 409, null));

        if (request.getName() != null) {
            customer.setName(request.getName());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }
        if (request.getSurname() != null) {
            customer.setSurname(request.getSurname());
        }

        customerRepository.save(customer);
        return CustomerMapper.toDTO(customer);
    }
}
