package com.example.javacourse.servicesTesting;

import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Grade;
import com.example.javacourse.entity.Teacher;
import com.example.javacourse.entity.commands.TeacherCommand;
import com.example.javacourse.entity.dto.TeacherDto;
import com.example.javacourse.excpetions.EntityWithExistingEmailException;
import com.example.javacourse.excpetions.NotExistingTeacherException;
import com.example.javacourse.excpetions.ObjectNotValidException;
import com.example.javacourse.repository.TeacherRepository;
import com.example.javacourse.service.TeacherService;
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

public class TeacherServiceTest {
    private TeacherService teacherService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectsValidator<TeacherCommand> teacherCommandObjectsValidator = new ObjectsValidator<>();
    private final ObjectsValidator<Long> longObjectsValidator = new ObjectsValidator<>();
    private final TeacherRepository teacherRepository = mock(TeacherRepository.class);

    @BeforeEach
    public void setup() {
        teacherService = new TeacherService(teacherRepository,
                modelMapper,
                longObjectsValidator,
                teacherCommandObjectsValidator);
    }

    @Test
    public void save_savingTeacher_teacherSaved(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kow",
                "email@gmial.com",
                "TWO",
                10.0);
        Teacher expectedTeacher = new Teacher(null,
                "Jan",
                "Kow",
                "email@gmial.com",
                Grade.TWO,
                10.0,
                Collections.emptySet(),
                false);
        //when
        teacherService.save(command);
        //then
        verify(teacherRepository).save(expectedTeacher);
    }

    @Test
    public void save_nullName_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand(null,
                "Kow",
                "email@gmial.com",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_emptyName_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("",
                "Kow",
                "email@gmial.com",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_nullLastName_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                null,
                "email@gmial.com",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_emptyLastName_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "",
                "email@gmial.com",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_nullObject_throwsObjectNotValidException(){
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> teacherService.save(null));
    }

    @Test
    public void save_emptyEmail_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_nullEmail_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                null,
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_invalidEmail_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "nieporpawnyemail",
                "TWO",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_emptyGarde_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "porpawny@email.com",
                "",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_nullGarde_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "porpawny@email.com",
                null,
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_invalidGarde_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "porpawny@email.com",
                "TEN",
                10.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_negativeRate_throwsObjectNotValidException(){
        //given
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                "porpawny@email.com",
                "TWO",
                -1.0);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void save_clientWithExistingEmail_throwChujWieCo(){
        //given
        String mail = "porpawny@email.com";
        TeacherCommand command = new TeacherCommand("Jan",
                "Kowal",
                mail,
                "TWO",
                1.0);
        Teacher teacher = new Teacher(null,
                "Jan",
                "Kow",
                mail,
                Grade.TWO,
                10.0,
                Collections.emptySet(),
                false);
        when(teacherRepository.findByEmail(mail)).thenReturn(Optional.of(teacher));
        //when/then
        Assertions.assertThrows(
                EntityWithExistingEmailException.class,
                () -> teacherService.save(command));
    }

    @Test
    public void remove_removingTeacher_teacherRemoved(){
        //given
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(new Teacher(1L,
                "Jan",
                "Kow",
                "mail@m.PL",
                Grade.TWO,
                10.0,
                Collections.emptySet(),
                false)));
        //when
        teacherService.remove(1L);
        //then
        verify(teacherRepository).deleteById(1L);
    }

    @Test
    public void remove_notExistingId_throwsNotExistingTeacherException(){
        //given/when/then
        Assertions.assertThrows(
                NotExistingTeacherException.class,
                () -> teacherService.remove(1));
    }

    @Test
    public void findById_findingTeacher_teacherFound(){
        //given
        Teacher expectedTeacher = new Teacher(1L,
                "Jan",
                "Kow",
                "mail@m.PL",
                Grade.TWO,
                10.0,
                Collections.emptySet(),
                false);
        when(teacherRepository.findByIdWithLessons(1L)).thenReturn(Optional.of(expectedTeacher));
        //when
        Teacher actualTeacher = teacherService.findById(1L);
        //then
        Assertions.assertEquals(expectedTeacher, actualTeacher);
    }

    @Test
    public void findById_notExistingId_throwsNotExistingTeacherException(){
        //given/when/then
        Assertions.assertThrows(
                NotExistingTeacherException.class,
                () -> teacherService.findById(1));
    }

    @Test
    public void findAll_findingAll_allDtoFound(){
        //given
        Page<Teacher> page = new PageImpl<>(List.of(
                new Teacher(1L, "Jan", "Kow", "mail@m.PL", Grade.TWO, 10.0, Collections.emptySet(), false),
                new Teacher(2L, "Jan", "Kowa", "mail2@m.PL", Grade.TWO, 10.0, Collections.emptySet(), false),
                new Teacher(3L, "Jan", "Kowal", "mail3@m.PL", Grade.TWO, 10.0, Collections.emptySet(), false)
        ));

        Page<TeacherDto> expectedPage = new PageImpl<>(List.of(
                new TeacherDto("Jan", "Kow", "mail@m.PL", Grade.TWO, 10.0, Collections.emptySet()),
                new TeacherDto("Jan", "Kowa", "mail2@m.PL", Grade.TWO, 10.0, Collections.emptySet()),
                new TeacherDto("Jan", "Kowal", "mail3@m.PL", Grade.TWO, 10.0, Collections.emptySet())
        ));

        when(teacherRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(page);
        //when
        Page<TeacherDto> actualPage = teacherService.findAll();
        //then
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    public void findAll_emptyPage_returnEmptyPage(){
        //given
        when(teacherRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(Page.empty());
        //when
        Page<TeacherDto> page = teacherService.findAll();
        //then
        Assertions.assertEquals(Page.empty(), page);
    }

}
