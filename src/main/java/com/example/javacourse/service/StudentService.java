package com.example.javacourse.service;

import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Lesson;
import com.example.javacourse.entity.Student;
import com.example.javacourse.entity.commands.StudentCommand;
import com.example.javacourse.entity.dto.StudentDto;
import com.example.javacourse.excpetions.EntityWithExistingEmailException;
import com.example.javacourse.excpetions.NotExistingStudentException;
import com.example.javacourse.excpetions.NotExistingTeacherException;
import com.example.javacourse.repository.StudentRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final ObjectsValidator<Long> longObjectsValidator;
    private final ObjectsValidator<StudentCommand> studentObjectsValidator;
    private final ObjectsValidator<Set<Long>> longsObjectsValidator;

    public void save(StudentCommand command){
        studentObjectsValidator.validate(command);

        if(studentRepository
                .findByEmail(command.getEmail())
                .isPresent()){
            throw new EntityWithExistingEmailException();
        }

        Student student = modelMapper.map(command, Student.class);
        student.setGrade(command.getGrade());

        studentRepository.save(student);
    }

    public void remove(@Min(1) @NotNull long id){
        longObjectsValidator.validate(id);
        if(studentRepository.findById(id).isEmpty()){
            throw new NotExistingTeacherException(
                    String.format("Student with id %d not exist", id));
        }
        studentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Set<Student> findAllById(@NotEmpty @NotNull @Size(min = 1)  Set<Long> students) {
        longsObjectsValidator.validate(students);

        Iterable<Student> iterableStudents = studentRepository.findAllByIdWithLessons(students);
        Set<Student> studentSet = new TreeSet<>(Comparator.comparing(Student::getId));
        iterableStudents.forEach(studentSet::add);
        if(studentSet.size() != students.size()){
            throw new NotExistingStudentException("Some students are not existing");
        }
        return studentSet;
    }

    @Transactional(readOnly = true)
    public Page<StudentDto> findAll() {
       return studentRepository.findAllWithLessons(PageRequest.of(0, 5, Sort.by("id")))
//        .map(s -> {
//            StudentDto studentDto = modelMapper.map(s, StudentDto.class);
//            studentDto.setLessons(s.getLessons().stream()
//                    .map(Lesson::getId)
//                    .collect(Collectors.toSet()));
//            return studentDto;
//        });
               .map(s -> new StudentDto(s.getName(),
                       s.getLastName(),
                       s.getEmail(),
                       s.getGrade(),
                       s.getLessons().stream()
                               .map(Lesson::getId)
                               .collect(Collectors.toSet()),
                       s.getRegistrationDate()));
    }

}
