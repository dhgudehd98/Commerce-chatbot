package com.sh.commercechatbot.config;

import com.sh.commercechatbot.llm.enums.LlmType;
import com.sh.commercechatbot.llm.service.LlmClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class LlmClientConfig {

    @Bean
    public Map<LlmType, LlmClientService> getLlmClient(List<LlmClientService> llmWebClientServiceList) {
        return llmWebClientServiceList
                .stream()
                .collect(Collectors.toMap(llmWebClientService -> llmWebClientService.getLlmType(), Function.identity()));
    }
}