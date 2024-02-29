package com.example.javacourse.service;

import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Classroom;
import com.example.javacourse.entity.Lesson;
import com.example.javacourse.entity.commands.ClassroomCommand;
import com.example.javacourse.entity.dto.ClassroomDto;
import com.example.javacourse.excpetions.NotExistingClassroomException;
import com.example.javacourse.repository.ClassroomRepository;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ObjectsValidator<ClassroomCommand> validator;
    private final ObjectsValidator<Long> longValidator;
    private final ModelMapper modelMapper;

    public void save(ClassroomCommand request){
        validator.validate(request);
        if(classroomRepository
                .findByLocation(request.getLocation())
                .isPresent()){
            throw new IllegalStateException(String.format("Classroom with location %d" +
                    "already exist", request.getLocation()));
        }

        Classroom classroom = modelMapper.map(request, Classroom.class);
        classroomRepository.save(classroom);
    }

    public void remove(@Positive long id){
        longValidator.validate(id);
        if(classroomRepository.findById(id).isEmpty()){
            throw new NotExistingClassroomException(
                    String.format("Classroom with id %d not exist", id));
        }
        classroomRepository.deleteById(id);
    }

    @Transactional
    public Classroom findById(@Positive @NotNull long id){
        longValidator.validate(id);
        return classroomRepository.findByIdWithLessons(id)
                .orElseThrow(() -> new NotExistingClassroomException(
                        String.format("Classroom with id %d not exist", id)));
    }

    @Transactional(readOnly = true)
    public Page<ClassroomDto> findAll(){
        return classroomRepository.findAllWithLessons(PageRequest.of(0,5, Sort.by("id")))
//                .map(e -> modelMapper.map(e, ClassroomDto.class));
                .map(e -> new ClassroomDto(e.getName(),
                        e.getLocation(),
                        e.getLessons().stream()
                            .map(Lesson::getId).collect(Collectors.toSet())));
    }

}
