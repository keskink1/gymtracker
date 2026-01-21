package com.keskin.users.service.impl;

import com.keskin.users.entity.RefreshToken;
import com.keskin.users.repository.RefreshTokenRepository;
import com.keskin.users.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Override
    public String createRefreshToken(String email) {
        refreshTokenRepository.findByEmail(email)
                .ifPresent(refreshTokenRepository::delete);

        String token = UUID.randomUUID().toString();
        long ttlInSeconds = refreshTokenExpiration / 1000;

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .email(email)
                .expirationInSeconds(ttlInSeconds)
                .expiryDate(LocalDateTime.now().plusSeconds(ttlInSeconds))
                .build();

        refreshTokenRepository.save(refreshToken);
        log.info("Refresh token created for user: {}", email);
        return token;
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(rt -> {
                    if (rt.getExpiryDate().isBefore(LocalDateTime.now())) {
                        refreshTokenRepository.delete(rt);
                        log.warn("Token expired for email: {}", rt.getEmail());
                        throw new RuntimeException("Refresh token was expired!");
                    }
                    return rt;
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));
    }

    @Override
    public void deleteByEmail(String email) {
        refreshTokenRepository.findByEmail(email).ifPresent(refreshTokenRepository::delete);
    }
}