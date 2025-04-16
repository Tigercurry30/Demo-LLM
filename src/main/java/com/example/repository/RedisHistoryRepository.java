package com.example.repository;

import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisHistoryRepository implements HistoryRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String CONVERSATION_RECORD_KEY_PREFIX = "conversation:record:";

    @Override
    public void insertHistoryIds(String type, String chatId) {
        String key = CONVERSATION_RECORD_KEY_PREFIX + type;
        redisTemplate.opsForSet().add(key, chatId);
    }

    @Override
    public List<String> getAllHistoryIdsByType(String type) {
        String key = CONVERSATION_RECORD_KEY_PREFIX + type;
        Set<String> chatIds = redisTemplate.opsForSet().members(key);
        return chatIds != null ?
                chatIds.stream()
                        .sorted(String::compareTo)
                        .toList() :
                List.of();
    }

}
