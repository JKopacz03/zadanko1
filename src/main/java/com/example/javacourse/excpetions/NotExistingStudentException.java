package com.example.javacourse.excpetions;

public class NotExistingStudentException extends RuntimeException {
    public NotExistingStudentException(String message) {
        super(message);
    }
}
