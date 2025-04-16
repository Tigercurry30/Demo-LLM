package com.example.repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisChatHistoryRepository implements HistoryRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String HISTORY_KEY_PREFIX = "chat:history:";
    private static final long DEFAULT_EXPIRATION_TIME = 24 * 60 * 60; // 24 hours

    @Override
    public void insertHistoryIds(String type, String chatId) {
        String key = HISTORY_KEY_PREFIX + type;
        redisTemplate.opsForSet().add(key, chatId);
        redisTemplate.expire(key, DEFAULT_EXPIRATION_TIME, java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public List<String> getAllHistoryIdsByType(String type) {
        String key = HISTORY_KEY_PREFIX + type;
        Set<Object> chatIds = redisTemplate.opsForSet().members(key);
        return chatIds != null ?
                chatIds.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()) :
                List.of();
    }
}
