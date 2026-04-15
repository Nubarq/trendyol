package com.ecommerce.trendyoldemo.entity;

public enum NotificationType {
    REVIEW_LEFT,          // customer left review for provider
    REVIEW_REPLY,         // provider replied to customer's review
    PROMOTION,            // discount or promo notification
    GENERAL               // fallback
}
