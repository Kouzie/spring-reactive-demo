package com.example.react.mongo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberLoginDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;

}
