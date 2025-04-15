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
public class CustomerServiceController {

    private final ChatClient serviceChatClient;
    private final HistoryRepository redisChatHistoryRepository;

    @RequestMapping(value = "/service", produces = "text/html;charset=utf-8")
    public Flux<String> service(String prompt, String chatId){

        //1. 保存会话id
        redisChatHistoryRepository.insertHistoryIds("service", chatId);

        //2.请求模型
        return serviceChatClient.prompt()
                .user(prompt)
                .user(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();

    }
}






















