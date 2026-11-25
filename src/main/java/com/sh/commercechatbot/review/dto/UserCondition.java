package com.sh.commercechatbot.review.dto;

import com.sh.commercechatbot.enums.review.FitPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCondition {

    private Integer height;
    private Integer weight;
    private FitPreference fitPreference;
}