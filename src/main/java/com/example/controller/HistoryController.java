package com.example.controller;

import java.util.List;

import com.example.entity.vo.MessageVO;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryRepository redisChatHistoryRepository;
    private final ChatMemory redisMemory;

    @GetMapping("/{type}")
    public List<String> getHistoryIds(@PathVariable("type") String type) {
        return redisChatHistoryRepository.getAllHistoryIdsByType(type);
    }

    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getHistory(@PathVariable("type") String type, @PathVariable("chatId") String chatId) {
        List<Message> message = redisMemory.get(chatId, Integer.MAX_VALUE);
        if(message == null) {
            return List.of();
        }
        return message.stream().map(MessageVO::new).toList();

    }

}