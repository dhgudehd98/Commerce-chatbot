package com.sh.commercechatbot.es.review.dto;

import com.sh.commercechatbot.enums.review.FitPreference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOptionalInfoDto {
    private Integer height;
    private Integer weight;
    private FitPreference fitPreference;
    private String preferenceQuery;

}