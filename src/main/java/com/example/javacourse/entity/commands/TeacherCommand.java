package com.example.javacourse.entity.commands;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherCommand {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "ONE|TWO|THREE|FOUR|FIVE|SIX")
    private String grade;
    @DecimalMin("0.0")
    private double rate;
}
