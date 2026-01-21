package com.keskin.users.repository;

import com.keskin.users.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByEmail(String email);
}