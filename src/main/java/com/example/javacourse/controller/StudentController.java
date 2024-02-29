package com.example.javacourse.controller;

import com.example.javacourse.entity.commands.StudentCommand;
import com.example.javacourse.entity.commands.TeacherCommand;
import com.example.javacourse.entity.dto.StudentDto;
import com.example.javacourse.entity.dto.TeacherDto;
import com.example.javacourse.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/student")
@RequiredArgsConstructor
@RestController
public class StudentController {
    private final StudentService studentService;


    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody StudentCommand command){
        studentService.save(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> remove(@RequestParam long id){
        studentService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<StudentDto>> findAll(){
        Page<StudentDto> students = studentService.findAll();
        System.out.println(students);
        return ResponseEntity.status(HttpStatus.FOUND).body(students);
    }
}
