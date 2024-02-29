package com.example.javacourse.servicesTesting;

import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Grade;
import com.example.javacourse.entity.Student;
import com.example.javacourse.entity.commands.StudentCommand;
import com.example.javacourse.entity.dto.StudentDto;
import com.example.javacourse.excpetions.NotExistingStudentException;
import com.example.javacourse.excpetions.NotExistingTeacherException;
import com.example.javacourse.excpetions.ObjectNotValidException;
import com.example.javacourse.repository.StudentRepository;
import com.example.javacourse.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class StudentServiceTest {
    private StudentService studentService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectsValidator<StudentCommand> studentCommandObjectsValidator = new ObjectsValidator<>();
    private final ObjectsValidator<Long> longObjectsValidator = new ObjectsValidator<>();
    private final ObjectsValidator<Set<Long>> longsObjectsValidator = new ObjectsValidator<>();
    private final StudentRepository studentRepository = mock(StudentRepository.class);

    @BeforeEach
    public void setup() {
        studentService = new StudentService(studentRepository,
                modelMapper,
                longObjectsValidator,
                studentCommandObjectsValidator,
                longsObjectsValidator);
    }

    @Test
    public void save_savingTeacher_teacherSaved(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "email@gmial.com",
                "TWO");
        Student expectedStudent = new Student(null,
                "Jan",
                "Kow",
                "email@gmial.com",
                Grade.TWO,
                Collections.emptySet(),
                LocalDate.now(),
                false);
        //when
        studentService.save(command);
        //then
        verify(studentRepository).save(expectedStudent);
    }

    @Test
    public void save_nullName_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand(null,
                "Kow",
                "email@gmial.com",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_emptyName_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("",
                "Kow",
                "email@gmial.com",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_nullLastName_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                null,
                "email@gmial.com",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_emptyLastName_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "",
                "email@gmial.com",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_nullObject_throwsObjectNotValidException(){
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> studentService.save(null));
    }

    @Test
    public void save_emptyEmail_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_nullEmail_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                null,
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_invalidEmail_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "valdiemail",
                "TWO");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_emptyGarde_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "email@gmial.com",
                "");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_nullGarde_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "email@gmial.com",
                null);
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void save_invalidGarde_throwsObjectNotValidException(){
        //given
        StudentCommand command = new StudentCommand("Jan",
                "Kow",
                "email@gmial.com",
                "TEN");
        //when/then
        Assertions.assertThrows(
                ObjectNotValidException.class,
                () -> studentService.save(command));
    }

    @Test
    public void remove_removingStudent_studentRemoved(){
        //given
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(null,
                "Jan",
                "Kow",
                "email@gmial.com",
                Grade.TWO,
                Collections.emptySet(),
                LocalDate.now(),
                false)));
        //when
        studentService.remove(1L);
        //then
        verify(studentRepository).deleteById(1L);
    }

    @Test
    public void remove_notExistingId_throwsNotExistingTeacherException(){
        //given/when/then
        Assertions.assertThrows(
                NotExistingTeacherException.class,
                () -> studentService.remove(1));
    }

    @Test
    public void findAllById_findingAll_foundAll(){
        //given
        Set<Student> expectedStudents = Set.of(
                new Student(1L, "Jan", "Kow", "email@gmial.com", Grade.TWO, Collections.emptySet(), LocalDate.now(), false),
                new Student(2L, "Jan", "Kow", "email1@gmial.com", Grade.TWO, Collections.emptySet(), LocalDate.now(), false),
                new Student(3L, "Jan", "Kow", "email2@gmial.com", Grade.TWO, Collections.emptySet(), LocalDate.now(), false)

        );
        when(studentRepository.findAllByIdWithLessons(Set.of(1L, 2L, 3L))).thenReturn(expectedStudents);
        //when
        Set<Student> actualStudents = studentService.findAllById(Set.of(1L, 2L, 3L));
        //then
        Assertions.assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void findAllById_NotExistingId_throwsNotExistingStudentException(){
        //given
        Set<Student> expectedStudents = Set.of(
                new Student(1L, "Jan", "Kow", "email@gmial.com", Grade.TWO, Collections.emptySet(), LocalDate.now(), false),
                new Student(2L, "Jan", "Kow", "email1@gmial.com", Grade.TWO, Collections.emptySet(), LocalDate.now(), false)
        );
        when(studentRepository.findAllByIdWithLessons(Set.of(1L, 2L, 3L))).thenReturn(expectedStudents);
        Assertions.assertThrows(
                NotExistingStudentException.class,
                () -> studentService.findAllById(Set.of(1L,2L,3L)));
    }

//    @Test
//    public void findAllById_emptyParameter_throws(){ //TODO
//        //given/when/then
//        Assertions.assertThrows(
//                ObjectNotValidException.class,
//                () -> studentService.findAllById(Collections.emptySet()));
//    }

    @Test
    public void findAllById_nullParameter_throws(){
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> studentService.findAllById(null));
    }


    @Test
    public void findAll_findingAll_allDtoFound(){
        //given
        Page<Student> page = new PageImpl<>(List.of(
                new Student(1L, "Jan", "Kow", "mail@m.PL", Grade.TWO, Collections.emptySet(), LocalDate.now(), false),
                new Student(2L, "Jan", "Kowa", "mail2@m.PL", Grade.TWO,  Collections.emptySet(),LocalDate.now(),  false),
                new Student(3L, "Jan", "Kowal", "mail3@m.PL", Grade.TWO, Collections.emptySet(), LocalDate.now(), false)
        ));

        Page<StudentDto> expectedPage = new PageImpl<>(List.of(
                new StudentDto("Jan", "Kow", "mail@m.PL", Grade.TWO, Collections.emptySet(), LocalDate.now()),
                new StudentDto("Jan", "Kowa", "mail2@m.PL", Grade.TWO,  Collections.emptySet(),LocalDate.now()),
                new StudentDto("Jan", "Kowal", "mail3@m.PL", Grade.TWO, Collections.emptySet(), LocalDate.now())
        ));

        when(studentRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(page);
        //when
        Page<StudentDto> actualPage = studentService.findAll();
        //then
        Assertions.assertEquals(expectedPage, actualPage);
    }

    @Test
    public void findAll_emptyPage_returnEmptyPage(){
        //given
        when(studentRepository.findAllWithLessons(
                PageRequest.of(0,5, Sort.by("id"))))
                .thenReturn(Page.empty());
        //when
        Page<StudentDto> page = studentService.findAll();
        //then
        Assertions.assertEquals(Page.empty(), page);
    }

}
