package com.example.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Msg {

    MessageType messageType;
    String text;
    Map<String, Object> metadata;
    List<AssistantMessage.ToolCall> toolCalls;

    public Msg(Message message){
        this.messageType = message.getMessageType();
        this.text = message.getText();
        this.metadata = message.getMetadata();
        if (message instanceof AssistantMessage assistantMessage) {
            this.toolCalls = assistantMessage.getToolCalls();
        }
    }

    public Message toMessage(){
        return switch (messageType){
            case USER -> new UserMessage(text, List.of(), metadata);
            case ASSISTANT -> new AssistantMessage(text, metadata, toolCalls,List.of());
            case SYSTEM -> new SystemMessage(text);
            default -> throw new IllegalArgumentException("Unsupported message type: " + messageType);
        };
    }
}
