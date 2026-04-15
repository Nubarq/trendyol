package com.ecommerce.trendyoldemo.service;


import com.ecommerce.trendyoldemo.dto.request.AddProductRequest;
import com.ecommerce.trendyoldemo.dto.response.ProductResponse;
import com.ecommerce.trendyoldemo.entity.ProductEntity;
import com.ecommerce.trendyoldemo.entity.ProviderEntity;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.mapper.ProductMapper;
import com.ecommerce.trendyoldemo.repository.ProductRepository;
import com.ecommerce.trendyoldemo.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final UserService userService;

    public ProductResponse addProduct(AddProductRequest request) {
        UserEntity currentUser = userService.getCurrentUser();

        ProviderEntity provider = providerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new CustomException(
                        "Company not found",
                        "Company not found for this user",
                        "Not Found",
                        404,
                        null
                ));

        // create product
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setProvider(provider);
        product.setAverageRating(0.0);

        productRepository.save(product);

        return ProductMapper.toDTO(product);
    }

    public Page<ProductResponse> getMyProducts(int page, int size) {
        UserEntity currentUser = userService.getCurrentUser();
        ProviderEntity provider = providerRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new CustomException("Company not found", "", "", 404, null));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ProductEntity> productPage = productRepository.findByProviderId(provider.getId(), pageable);

        // map each entity to response
        return productPage.map(ProductMapper::toDTO);
    }

    public Page<ProductResponse> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findAll( pageable);

        // map each entity to response
        return productPage.map(ProductMapper::toDTO);
    }


    public ProductResponse updateProduct(Long id, AddProductRequest request){
        UserEntity user= userService.getCurrentUser();
        ProviderEntity provider = providerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomException("Company not found", "", "", 404, null));

        ProductEntity product=productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found", "", "", 404, null));

        if (!product.getProvider().getId().equals(provider.getId())) {
            throw new CustomException("Sizin bu məhsulun məlumatlarını dəyişməyə icazəniz yoxdur", "",
                    "", 403, null);
        }

        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        productRepository.save(product);
        return ProductMapper.toDTO(product);
    }
    }
