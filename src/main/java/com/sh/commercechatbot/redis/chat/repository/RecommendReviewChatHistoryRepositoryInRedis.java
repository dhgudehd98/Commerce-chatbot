package com.sh.commercechatbot.redis.chat.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.commercechatbot.review.dto.RecommendHistory;
import com.sh.commercechatbot.review.dto.ReviewRecommendResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class RecommendReviewChatHistoryRepositoryInRedis {
    private static final String KEY_PREFIX = "chatbot:review-recommend-history:";
    private static final Duration STATE_TTL = Duration.ofMinutes(60);
    private final ReactiveStringRedisTemplate redisTemplate;
    private final ObjectMapper mapper;

    private String createKey(Long memberId) {
        log.info("[새로운 대화 생성] memberId : {}", memberId);
        return KEY_PREFIX + memberId;
    }

    public Mono<ReviewRecommendResultDto> findRecommendResult(Long memberId) {
        String key = createKey(memberId);

        return redisTemplate.opsForValue()
                .get(key)
                .map(stateString -> {
                    try {
                        return mapper.readValue(stateString, ReviewRecommendResultDto.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public Mono<Boolean> save(Long memberId, ReviewRecommendResultDto recommendResultDto, RecommendHistory recommendHistory) throws JsonProcessingException {

        if(recommendResultDto != null) recommendResultDto.addHistory(recommendHistory);
        String key = createKey(memberId);
        String jsonState = mapper.writeValueAsString(recommendResultDto);


        return redisTemplate.opsForValue()
                .set(key, jsonState, STATE_TTL);
    }

    public Mono<Boolean> delete(Long memberId) {
        String key = createKey(memberId);
        log.info("[사용자 대화 State 삭제] memberId : {}",memberId);

        return redisTemplate.delete(key)
                .map(deletedCount -> deletedCount > 0);
    }

}