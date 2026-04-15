package com.ecommerce.trendyoldemo.dto;


import com.ecommerce.trendyoldemo.entity.NotificationEntity;
import com.ecommerce.trendyoldemo.entity.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    private Long id;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
    private NotificationType type;

    public static NotificationDTO fromEntity(NotificationEntity entity) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setRead(entity.isRead());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setType(entity.getType());
        return dto;
    }
}
