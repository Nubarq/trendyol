package com.ecommerce.trendyoldemo.dto.response;

import lombok.Data;

@Data
public class PasswordResetResponse {
	private String email;
	private String token;
	private String newPassword;
}
