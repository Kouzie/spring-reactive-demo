package com.example.react.mongo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Member {

    @Id
    private ObjectId id;
    private String userName;
    private String password;
    private String name;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
