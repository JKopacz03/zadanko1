package com.example.javacourse.excpetions;

public class NotExistingClassroomException extends RuntimeException {
    public NotExistingClassroomException(String message) {
        super(message);
    }
}
