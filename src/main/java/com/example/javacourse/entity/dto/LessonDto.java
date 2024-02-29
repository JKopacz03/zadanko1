package com.example.javacourse.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LessonDto {
    private Long teacher;
    private Set<Long> students;
    private Long classroom;
    private LocalDateTime dateTime;
    private String topic;
}
