package com.example.react.r2dbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("book")
public class Book {
    @Id
    private Long id;
    private String title;
    private Integer publishingYear;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
