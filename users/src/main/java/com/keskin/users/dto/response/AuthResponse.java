package com.keskin.users.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String email;
}