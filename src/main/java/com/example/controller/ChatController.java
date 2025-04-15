package com.example.controller;

import com.example.repository.HistoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

import org.springframework.ai.model.Media;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController {

    private final HistoryRepository historyRepository;
    private final ChatClient chatClient;

    @RequestMapping(value = "/chat", produces = "text/html; charset=utf-8")
    public Flux<String> chat(
            @RequestParam("prompt") String prompt,
            @RequestParam("chatId") String chatId,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        historyRepository.insertHistoryIds("chat", chatId);

        if(files == null || files.isEmpty()){
            return TextChat(prompt, chatId);
        }else{
            //多模态
            return MultiModalChat(prompt, chatId, files);
        }

    }

    private Flux<String> MultiModalChat(String prompt, String chatId, List<MultipartFile> files) {

        //1. 解析多媒体
        List<Media> medias = files.stream()
                .map(file -> new Media(
                                MimeType.valueOf(Objects.requireNonNull(file.getContentType())),
                                file.getResource()
                        )
                )
                .toList();

        //2. 请求模型
        return chatClient.prompt()
                .user(p -> p.text(prompt).media(medias.toArray(Media[]::new)))
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }

    private Flux<String> TextChat(String prompt, String chatId) {
        return chatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }


}
