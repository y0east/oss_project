package kr.ac.hufs.ice.ice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";
    private static final String REFRESH_PREFIX = "refresh:";

    public void blacklistAccessToken(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", expirationMillis, TimeUnit.MILLISECONDS);
    }

    public void deleteRefreshToken(String studentId) {
        redisTemplate.delete(REFRESH_PREFIX + studentId);
    }

    public void saveRefreshToken(String studentId, String refreshToken, long expirationMillis) {
        redisTemplate.opsForValue().set(REFRESH_PREFIX + studentId, refreshToken, expirationMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
