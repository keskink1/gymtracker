package com.keskin.users.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "RefreshToken")
public class RefreshToken implements Serializable {

    @Id
    private String token;

    @Indexed
    private String email;

    @TimeToLive
    private Long expirationInSeconds;

    private LocalDateTime expiryDate;
}