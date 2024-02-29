package com.example.javacourse.controller;

import com.example.javacourse.entity.Teacher;
import com.example.javacourse.entity.commands.TeacherCommand;
import com.example.javacourse.entity.dto.TeacherDto;
import com.example.javacourse.repository.TeacherRepository;
import com.example.javacourse.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody TeacherCommand command){
        teacherService.save(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> remove(@RequestParam long id){
        teacherService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<TeacherDto>> findAll(){
        Page<TeacherDto> teachers = teacherService.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(teachers);
    }
}
