package com.sh.commercechatbot.chat.controller;


import com.sh.commercechatbot.chat.dto.request.ChatRequestDto;
import com.sh.commercechatbot.chat.dto.response.ChatResponseDto;
import com.sh.commercechatbot.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("")
    public Flux<ChatResponseDto> chat(
            @RequestBody ChatRequestDto request
    ){
        return chatService.chat(request);
    }

}