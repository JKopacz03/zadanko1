package com.example.javacourse.handler;

import com.example.javacourse.excpetions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleObjectNotValidException(ObjectNotValidException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getErrorMessages());
    }

    @ExceptionHandler(NotExistingStudentException.class)
    public ResponseEntity<?> handleNotExistingStudentException(NotExistingStudentException exception){
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(NotExistingTeacherException.class)
    public ResponseEntity<?> handleNotExistingTeacherException(NotExistingTeacherException exception){
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(NotExistingClassroomException.class)
    public ResponseEntity<?> handleNotExistingClassroomException(NotExistingClassroomException exception){
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(InvalidLessonTimeException.class)
    public ResponseEntity<?> handleInvalidLessonTimeException(InvalidLessonTimeException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(EntityWithExistingEmailException.class)
    public ResponseEntity<?> handleEntityWithExistingEmailException(EntityWithExistingEmailException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

}
