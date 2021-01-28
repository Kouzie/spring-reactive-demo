package com.example.react.r2dbc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentDetail {
    private Long rentId;
    private Long memberId;
    private Long bookId;
    private String name;
    private String title;
}
