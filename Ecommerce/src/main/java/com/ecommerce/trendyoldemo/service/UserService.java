package com.ecommerce.trendyoldemo.service;

import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.exception.CustomException;
import com.ecommerce.trendyoldemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void isUserExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new CustomException("İstifadəçi artıq mövcuddur", "User already exists", "Already Exists",
                    403, null);
        }
    }

    public UserEntity getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("İstifadəçi tapılmadı", "User not found", "Not found",
                        404, null));
    }
}
