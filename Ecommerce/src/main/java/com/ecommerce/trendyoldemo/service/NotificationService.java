package com.ecommerce.trendyoldemo.service;


import com.ecommerce.trendyoldemo.entity.NotificationEntity;
import com.ecommerce.trendyoldemo.entity.NotificationType;
import com.ecommerce.trendyoldemo.entity.UserEntity;
import com.ecommerce.trendyoldemo.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createNotification(UserEntity user, String message, NotificationType type) {
        NotificationEntity n = new NotificationEntity();
        n.setUser(user);
        n.setMessage(message);
        n.setType(type);
        n.setRead(false);
        n.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(n);
    }
    public List<NotificationEntity> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void markAsRead(Long notificationId) {
        NotificationEntity notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notif.setRead(true);
        notificationRepository.save(notif);
    }
}
