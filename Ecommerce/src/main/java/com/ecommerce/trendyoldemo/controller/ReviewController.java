package com.ecommerce.trendyoldemo.controller;


import com.ecommerce.trendyoldemo.dto.request.AddReviewRequest;
import com.ecommerce.trendyoldemo.dto.response.MessageResponse;
import com.ecommerce.trendyoldemo.dto.response.ReviewResponse;
import com.ecommerce.trendyoldemo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@MyRestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<MessageResponse> addReview(@RequestBody AddReviewRequest request) {
        return ResponseEntity.ok(reviewService.addReview(request));
    }
    @GetMapping("/getReviews")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @RequestParam Long targetId) {
        return ResponseEntity.ok(reviewService.getReviewsForTarget(targetId));
    }
}
