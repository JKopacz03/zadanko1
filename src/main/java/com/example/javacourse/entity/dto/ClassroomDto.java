package com.example.javacourse.entity.dto;

import com.example.javacourse.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ClassroomDto {
    private String name;
    private int location;
    private Set<Long> lessons;

}
