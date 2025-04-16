package com.example.controller;


import com.example.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class GameController {

    private final ChatClient gameChatClient;
    private final HistoryRepository historyRepository;

    @RequestMapping(value = "/game", produces = "text/html; charset=utf-8")
    public Flux<String> game(String prompt, String chatId) {

        historyRepository.insertHistoryIds("game", chatId);

        return gameChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}
