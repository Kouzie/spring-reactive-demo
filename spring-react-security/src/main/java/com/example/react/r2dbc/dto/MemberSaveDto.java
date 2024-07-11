package com.example.react.r2dbc.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberSaveDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
