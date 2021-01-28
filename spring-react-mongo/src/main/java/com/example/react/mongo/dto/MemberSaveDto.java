package com.example.react.mongo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

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
