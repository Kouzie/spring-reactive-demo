package com.example.react.r2dbc.component;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RandomValueDto {
    private Integer randomViews;
    private Long randomVolume;
    private Integer randomPrice;
    private Long updated;
}
