package com.example.javacourse.entity.commands;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassroomCommand {
    @NotEmpty
    @NotNull
    private String name;
    @NotNull
    @Min(1)
    private int location;
}
