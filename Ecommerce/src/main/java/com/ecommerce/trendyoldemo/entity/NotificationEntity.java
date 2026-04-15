package com.ecommerce.trendyoldemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;   // e.g. REVIEW_LEFT, REVIEW_REPLY, PROMO

    private String message;          // message shown to user

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;         // who will receive this notification

    private boolean read = false;

    private LocalDateTime createdAt = LocalDateTime.now();

}
