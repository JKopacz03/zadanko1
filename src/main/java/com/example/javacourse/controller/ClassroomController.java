package com.example.javacourse.controller;

import com.example.javacourse.entity.commands.ClassroomCommand;
import com.example.javacourse.entity.dto.ClassroomDto;
import com.example.javacourse.service.ClassroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/classroom")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody ClassroomCommand command){
        classroomService.save(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> remove(@RequestParam long id){
        classroomService.remove(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ClassroomDto>> findAll(){
        Page<ClassroomDto> classrooms = classroomService.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(classrooms);
    }

}
