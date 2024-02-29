package com.example.javacourse.excpetions;

public class InvalidLessonTimeException extends RuntimeException {
    public InvalidLessonTimeException(String message) {
        super(message);
    }
}
