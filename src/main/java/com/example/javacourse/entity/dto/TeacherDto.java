package com.example.javacourse.entity.dto;

import com.example.javacourse.entity.Grade;
import com.example.javacourse.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TeacherDto {
    private String name;
    private String lastName;
    private String email;
    private Grade grade;
    private double rate;
    private Set<Long> lessons;
}
