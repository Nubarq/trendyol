package com.ecommerce.trendyoldemo.service;

import com.ecommerce.trendyoldemo.dto.response.OrderItemResponse;
import com.ecommerce.trendyoldemo.dto.response.OrderResponse;
import com.ecommerce.trendyoldemo.entity.*;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.mapper.OrderItemMapper;
import com.ecommerce.trendyoldemo.mapper.OrderMapper;
import com.ecommerce.trendyoldemo.mapper.ProductMapper;
import com.ecommerce.trendyoldemo.repository.*;
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
public class OrderService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final VerificationService verificationService;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenUtil refreshTokenUtil;



    public OrderItemResponse addItemToOrder(Long productId, Integer quatntity){
        UserEntity currentUser = userService.getCurrentUser();
        CustomerEntity customer= customerRepository.findByUserId(currentUser.getId()).orElseThrow(()
                ->new CustomException(
                "Istifadeci tapılmadı",
                "Customer not found",
                "Not Found",
                404, null));
        ProductEntity product= productRepository.findById(productId).orElseThrow(() ->
                new CustomException(
                        "Mehsul tapılmadı",
                        "Product not found",
                        "Not Found",
                        404, null));

        OrderEntity order = orderRepository.findByCustomerId(customer.getId()).orElseThrow(() ->
                new CustomException(
                        "Mehsul tapılmadı",
                        "Product not found",
                        "Not Found",
                        404, null));
        double unitPrice = product.getPrice()*quatntity;
        double totalPrice = order.getTotalPrice() +unitPrice;
        order.setTotalPrice(totalPrice);
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setProduct(product);
        orderItem.setUnitPrice(unitPrice);
        orderItem.setQuantity(quatntity);
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        return OrderItemMapper.toDTO(orderItem);
    }

    public OrderItemResponse updateOrderItem(Long id, Integer quantity){
        UserEntity currentUser = userService.getCurrentUser();
        CustomerEntity customer= customerRepository.findByUserId(currentUser.getId()).orElseThrow(()
                ->new CustomException(
                "Istifadeci tapılmadı",
                "Customer not found",
                "Not Found",
                404, null));
        OrderItemEntity orderItem =orderItemRepository.findById(id).orElseThrow(()
                ->new CustomException(
                "sebetiniz tapılmadı",
                "Order not found",
                "Not Found",
                404, null));
        if(!orderItem.getOrder().getId().equals(customer.getOrder())){
            throw new CustomException("Sizin bu məhsulun məlumatlarını dəyişməyə icazəniz yoxdur", "",
                    "", 403, null);
        }

        orderItem.setQuantity(quantity);

        return OrderItemMapper.toDTO(orderItem);

    }
    public Page<OrderResponse> getMyOrder(int page, int size){
        UserEntity currentUser = userService.getCurrentUser();
        CustomerEntity customer = customerRepository.findByUserId(currentUser.getId()).orElseThrow(()
                -> new CustomException("Istifadeci tapilmadi", "Customer not found", "", 404, null));
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderPage = orderRepository.findByCustomerId(customer.getId(), pageable);

        // map each entity to response
        return orderPage.map(OrderMapper::toDTO);
    }






}
