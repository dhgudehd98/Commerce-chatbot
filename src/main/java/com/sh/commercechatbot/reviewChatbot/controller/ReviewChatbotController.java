package com.sh.commercechatbot.reviewChatbot.controller;


import com.sh.commercechatbot.reviewChatbot.dto.request.ChatRequestDto;
import com.sh.commercechatbot.reviewChatbot.dto.response.ChatResponseDto;
import com.sh.commercechatbot.reviewChatbot.service.ReviewChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ReviewChatbotController {

    private final ReviewChatbotService reviewChatbotService;

    @PostMapping("")
    public Flux<ChatResponseDto> chat(
            @RequestBody ChatRequestDto request
    ){
        return reviewChatbotService.chat(request);
    }

}