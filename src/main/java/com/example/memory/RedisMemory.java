package com.example.memory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisMemory implements ChatMemory {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CHAT_MEMORY_KEY_PREFIX = "chat:memory:";
    private static final long DEFAULT_EXPIRATION_TIME = 24 * 60 * 60; // 24 hours


    @Override
    public void add(String conversationId, List<Message> messages) {
        String key = CHAT_MEMORY_KEY_PREFIX + conversationId;
        redisTemplate.opsForValue().set(key, messages, DEFAULT_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> get(String conversationId, int lastN) {
        String key = CHAT_MEMORY_KEY_PREFIX + conversationId;
        List<Message> messages = (List<Message>) redisTemplate.opsForValue().get(key);
        return messages != null ? messages.subList(0, Math.min(messages.size(), lastN)) : List.of();
    }

    @Override
    public void clear(String conversationId) {
        String key = CHAT_MEMORY_KEY_PREFIX + conversationId;
        redisTemplate.delete(key);
    }

} 