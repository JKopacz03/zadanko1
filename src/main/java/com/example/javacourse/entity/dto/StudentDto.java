package com.example.javacourse.entity.dto;

import com.example.javacourse.entity.Grade;
import com.example.javacourse.entity.Lesson;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private String name;
    private String lastName;
    private String email;
    private Grade grade;
    private Set<Long> lessons;
    private LocalDate registrationDate;

}
