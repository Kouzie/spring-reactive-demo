package com.example.react.r2dbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * dashboard 조회 정렬 태그
 * */
@Getter
@AllArgsConstructor
public enum SortTag {
    VIEWS("views","조회순"),
    VOLUME("volume","거래량순"),
    RATE("rate","변동순"),
    ;
    private String key;
    private String desc;

    public static SortTag keyOf(String key) {
        for (SortTag value : values()) {
            if (value.key.equals(key))
                return value;
        }
        throw new IllegalArgumentException("invalid sort key:" + key);
    }
}