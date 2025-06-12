package kr.ac.hufs.ice.ice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "blacklist:";

    // 여기서 expireationMillis는 AccessToken 만료 기간이랑 같게 설정
    public void blacklistToken(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "logout", expirationMillis, TimeUnit.MILLISECONDS);
    }

    public boolean isBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
