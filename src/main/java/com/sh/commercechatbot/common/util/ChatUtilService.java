package com.sh.commercechatbot.common.util;

import com.sh.commercechatbot.reviewChatbot.dto.response.ConversationAnalysisResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatUtilService {

    private final ObjectMapper mapper;

    public ConversationAnalysisResponseDto parseToConversationResponse(String llmResponse) {
        log.info("[JSON Parsing 전 LLM Response 출력] LLM 응답값 : {}", llmResponse);
        String parseToJsonString = extractedJsonString(llmResponse);
        try {
            log.info("[LLM Parsing Start to ConversationAnalysisResponseDto] JsonString : " + parseToJsonString);
            return mapper.readValue(parseToJsonString, ConversationAnalysisResponseDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("[LLM Parsing Fail To ConversationAnalysisResponseDto] ParsingError Message :" +  e.getMessage());
        }
    }
    public String extractedJsonString(String response) {
        int fi = response.indexOf('{');
        int li = response.lastIndexOf('}');

        if (fi != -1 && li != -1 && fi < li) {
            log.info("[Extract Json String] : " + response.substring(fi, li + 1));
            return response.substring(fi, li + 1);
        }

        return "";
    }
}