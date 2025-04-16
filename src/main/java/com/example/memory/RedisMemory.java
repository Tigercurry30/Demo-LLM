package com.example.memory;

import java.util.List;

import com.example.entity.po.Msg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisMemory implements ChatMemory {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String MEMORY_KEY_PREFIX = "memory:";



    @Override
    public void add(String conversationId, List<Message> messages) {

        if(messages == null){
            return;
        }
        List<String> list = messages.stream().map(Msg::new).map(msg -> {
            try {
                return objectMapper.writeValueAsString(msg);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        String key = MEMORY_KEY_PREFIX + conversationId;
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Message> get(String conversationId, int lastN) {

        String key = MEMORY_KEY_PREFIX + conversationId;
        List<String> list = redisTemplate.opsForList().range(key, 0, lastN);

        if(list == null || list.isEmpty()){
            return List.of();
        }

        return list.stream().map(s -> {
            try {
                return objectMapper.readValue(s, Msg.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).map(Msg::toMessage).toList();

    }

    @Override
    public void clear(String conversationId) {
        String key = MEMORY_KEY_PREFIX + conversationId;
        redisTemplate.delete(key);
    }
} 