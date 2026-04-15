package com.ecommerce.trendyoldemo.service;


import com.ecommerce.trendyoldemo.dto.request.AddReviewRequest;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.entity.ProductEntity;
import com.ecommerce.trendyoldemo.entity.ReviewEntity;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.mapper.ReviewMapper;
import com.ecommerce.trendyoldemo.repository.ProductRepository;
import com.ecommerce.trendyoldemo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;




    public MessageResponse addReview(AddReviewRequest request) {
        UserEntity currentUser = userService.getCurrentUser();

        ReviewEntity review = new ReviewEntity();
        review.setStars(request.getStars());
        review.setText(request.getCommentText());
        review.setUser(currentUser);

        handleProductReview(request, currentUser, review);

        reviewRepository.save(review);

        MessageResponse response = new MessageResponse();
        response.setMessage("Review added successfully!");
        return response;
    }

    //  Get all reviews for a service or product
    public List<ReviewResponse> getReviewsForTarget(Long targetId) {
        List<ReviewEntity> reviews;

        reviews = reviewRepository.findByProductId(targetId);
        return ReviewMapper.toDTOList(reviews);
    }


//    private void handleProviderServiceReview(AddReviewRequest request, UserEntity currentUser, ReviewEntity review) {
//        ProductEntity providerService = productRepository.findById(request.getTargetId())
//                .orElseThrow(() -> new CustomException("Provider service not found", "", "", 404, null));
//
//        if (reviewRepository.existsByUserIdAndProviderServiceId(currentUser.getId(), providerService.getId())) {
//            throw new CustomException("You already reviewed this provider service", "", "", 409, null);
//        }
//
//        review.setProduct(providerService);
//    }

    private void handleProductReview(AddReviewRequest request, UserEntity currentUser, ReviewEntity review) {
        ProductEntity product = productRepository.findById(request.getTargetId())
                .orElseThrow(() -> new CustomException("Product not found", "", "", 404, null));

        if (reviewRepository.existsByUserIdAndProductId(currentUser.getId(), product.getId())) {
            throw new CustomException("You already reviewed this product", "", "", 409, null);
        }

        review.setProduct(product);
    }
}
