package com.example.javacourse.excpetions;

public class NotExistingTeacherException extends RuntimeException {
    public NotExistingTeacherException(String message) {
        super(message);
    }
}
