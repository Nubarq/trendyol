package com.ecommerce.trendyoldemo.controller;



import com.ecommerce.trendyoldemo.dto.NotificationDTO;
import com.ecommerce.trendyoldemo.entity.NotificationType;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.service.NotificationService;
import com.ecommerce.trendyoldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@MyRestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService service;
    private final UserService userService;
    @PostMapping("/addNotification")
    public void createNotification(UserEntity user, String message, NotificationType type) {
        service.createNotification(user, message, type);
    }

    @GetMapping
    public List<NotificationDTO> getMyNotifications() {
        Long userId = userService.getCurrentUser().getId();
        return service.getUserNotifications(userId)
                .stream().map(NotificationDTO::fromEntity)
                .toList();
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
    }

}
