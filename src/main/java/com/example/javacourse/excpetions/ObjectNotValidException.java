package com.example.javacourse.excpetions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Data
public class ObjectNotValidException extends RuntimeException{
    private final Set<String> errorMessages;
}
