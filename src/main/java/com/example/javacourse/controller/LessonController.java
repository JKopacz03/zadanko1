package com.example.javacourse.controller;

import com.example.javacourse.entity.commands.LessonCommand;
import com.example.javacourse.entity.commands.TeacherCommand;
import com.example.javacourse.entity.dto.LessonDto;
import com.example.javacourse.entity.dto.TeacherDto;
import com.example.javacourse.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody LessonCommand command){
        lessonService.save(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> remove(@RequestParam long id){
        lessonService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<LessonDto>> findAll(){
        Page<LessonDto> lessons = lessonService.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(lessons);
    }
}
