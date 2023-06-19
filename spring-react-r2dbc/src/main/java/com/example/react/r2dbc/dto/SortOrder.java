package com.example.react.r2dbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * dashboard 조회 정렬 순서
 * */
@Getter
@AllArgsConstructor
public enum SortOrder {
    ASC("asc"),
    DESC("desc"),
    ;
    private String key;

    public static SortOrder keyOf(String key) {
        for (SortOrder value : values()) {
            if (value.key.equals(key))
                return value;
        }
        throw new IllegalArgumentException("invalid sort key:" + key);
    }
}
