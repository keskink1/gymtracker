package com.keskin.users.service;

import com.keskin.users.entity.RefreshToken;

public interface IRefreshTokenService {
    String createRefreshToken(String email);
    RefreshToken verifyRefreshToken(String token);
    void deleteByEmail(String email);
}