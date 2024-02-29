package com.example.javacourse.entity.commands;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class LessonCommand {
    @Min(1)
    private Long teacher;
    @NotNull
    @NotEmpty
    private Set<Long> students;
    @Min(1)
    private Long classroom;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    @NotNull
    @NotEmpty
    private String topic;
}
