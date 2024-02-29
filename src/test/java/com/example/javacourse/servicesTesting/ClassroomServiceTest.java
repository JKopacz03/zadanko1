package com.example.javacourse.servicesTesting;


import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Classroom;
import com.example.javacourse.entity.commands.ClassroomCommand;
import com.example.javacourse.entity.dto.ClassroomDto;
import com.example.javacourse.excpetions.NotExistingClassroomException;
import com.example.javacourse.excpetions.ObjectNotValidException;
import com.example.javacourse.repository.ClassroomRepository;
import com.example.javacourse.service.ClassroomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ClassroomServiceTest {
    private ClassroomService classroomService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectsValidator<ClassroomCommand> classroomCommandObjectsValidator = new ObjectsValidator<>();
    private final ObjectsValidator<Long> longObjectsValidator = new ObjectsValidator<>();
    private final ClassroomRepository classroomRepository= mock(ClassroomRepository.class);

    @BeforeEach
    public void setup() {
        classroomService = new ClassroomService(classroomRepository,
                classroomCommandObjectsValidator,
                longObjectsValidator,
                modelMapper);
    }

    @Test
    public void save_savingClassroom_classroomSaved(){
        //given
        ClassroomCommand command = new ClassroomCommand("Sql class", 1);
        Classroom expectedClassroom = new Classroom(null,
                "Sql class",
                1,
                new HashSet<>(),
                false);
        //when
        classroomService.save(command);
        //then
        verify(classroomRepository).save(expectedClassroom);
    }

    @Test
    public void save_nullName_throwsObjectNotValidException(){
        //given
        ClassroomCommand command = new ClassroomCommand(null, 1);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> classroomService.save(command));
    }

    @Test
    public void save_emptyName_throwsObjectNotValidException(){
        //given
        ClassroomCommand command = new ClassroomCommand("", 1);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> classroomService.save(command));
    }

    @Test
    public void save_0Location_throwsObjectNotValidException(){
        //given
        ClassroomCommand command = new ClassroomCommand("name", 0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> classroomService.save(command));
    }

    @Test
    public void save_negativeLocation_throwsObjectNotValidException(){
        //given
        ClassroomCommand command = new ClassroomCommand("name", -20);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> classroomService.save(command));
    }

    @Test
    public void save_nullCommand_throwsIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> classroomService.save(null));
    }

    @Test
    public void save_existingLocation_throwsIllegalStateException(){
        //given
        ClassroomCommand command = new ClassroomCommand("name", 1);
        Classroom existingClassroom = new Classroom(1L,
                "name2",
                1,
                new HashSet<>(),
                false);

        when(classroomRepository.findByLocation(1)).thenReturn(Optional.of(existingClassroom));
        //when/then
        Assertions.assertThrows(
                IllegalStateException.class,
                () -> classroomService.save(command));
    }

    @Test
    public void remove_removingClassroom_classroomRemoved(){
        //given
        when(classroomRepository.findById(1L)).thenReturn(Optional.of(new Classroom(1L,
                "xd",
                1,
                Collections.emptySet(),
                false)));
        //when
        classroomService.remove(1L);
        //then
        verify(classroomRepository).deleteById(1L);
    }
//
//    @Test
//    public void remove_0id_throwsObjectNotValidException(){ //TODO
//        //given/when/then
//        Assertions.assertThrows(
//                ObjectNotValidException.class,
//                () -> classroomService.remove(-1));
//    }

    @Test
    public void remove_notExistingId_throwsNotExistingClassroomException(){
        //given/when/then
        Assertions.assertThrows(
                NotExistingClassroomException.class,
                () -> classroomService.remove(1));
    }

    @Test
    public void findById_findingClassroom_classroomFound(){
        //given
        Classroom expectedClassroom = new Classroom(1L,
                "Sql class",
                1,
                new HashSet<>(),
                false);
        when(classroomRepository.findByIdWithLessons(1L)).thenReturn(Optional.of(expectedClassroom));
        //when
        Classroom actualClassroom = classroomService.findById(1L);
        //then
        Assertions.assertEquals(expectedClassroom, actualClassroom);
    }

    @Test
    public void findById_notExistingId_throwsNotExistingClassroomException(){
        //given/when/then
        Assertions.assertThrows(
                NotExistingClassroomException.class,
                () -> classroomService.findById(1));
    }

//    @Test
//    public void findById_0id_throwsObjectNotValidException(){ //TODO
//        //given/when/then
//        Assertions.assertThrows(
//                ObjectNotValidException.class,
//                () -> classroomService.findById(-1));
//    }

    @Test
    public void findAll_findingAll_allDtoFound(){
        //given
        Page<Classroom> page = new PageImpl<>(List.of(
                new Classroom(1L,"a", 1, Collections.emptySet(), false),
                new Classroom(2L, "b", 2, Collections.emptySet(), false),
                new Classroom(3L, "c", 3, Collections.emptySet(), false)));

        Page<ClassroomDto> expectedPage = new PageImpl<>(List.of(
                new ClassroomDto("a", 1, Collections.emptySet()),
                new ClassroomDto( "b", 2, Collections.emptySet()),
                new ClassroomDto( "c", 3, Collections.emptySet())));

        when(classroomRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(page);
        //when
        Page<ClassroomDto> actualPage = classroomService.findAll();
        //then
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    public void findAll_emptyPage_returnEmptyPage(){
        //given
        when(classroomRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(Page.empty());
        //when
        Page<ClassroomDto> page = classroomService.findAll();
        //then
        Assertions.assertEquals(Page.empty(), page);
    }



}

