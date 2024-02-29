package com.example.javacourse.service;

import com.example.javacourse.config.ObjectsValidator;
import com.example.javacourse.entity.Classroom;
import com.example.javacourse.entity.Lesson;
import com.example.javacourse.entity.Student;
import com.example.javacourse.entity.Teacher;
import com.example.javacourse.entity.commands.LessonCommand;
import com.example.javacourse.entity.dto.LessonDto;
import com.example.javacourse.repository.LessonRepository;
import com.example.javacourse.excpetions.InvalidLessonTimeException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final TeacherService teacherService;
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final ObjectsValidator<LessonCommand> lessonValidator;
    private final ObjectsValidator<Long> longValidator;
    private final EmailService emailService;

    @Transactional
    public void save(LessonCommand command){
        lessonValidator.validate(command);
        Teacher teacher = teacherService.findById(command.getTeacher());
        Set<Student> students = studentService.findAllById(command.getStudents());
        Classroom classroom = classroomService.findById(command.getClassroom());

        lessonTimeValidation(command, teacher, students, classroom);

        Lesson lesson = modelMapper.map(command, Lesson.class);
        lesson.setTeacher(teacher);
        lesson.setStudents(students);
        lesson.setClassroom(classroom);
        lessonRepository.save(lesson);
        sendConfirmEmail(teacher, students, lesson);
    }

    private void sendConfirmEmail(Teacher teacher,
                                  Set<Student> students,
                                  Lesson lesson) {
        String content = "Please confirm your present on lesson: "
                + lesson.getTopic() + ", at "
                + lesson.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        emailService.send(teacher.getEmail(), content);
        students.forEach(s -> {
            emailService.send(s.getEmail(), content);
        });
    }

    @Transactional
    public void remove(@Min(1) @NotNull long id){
        longValidator.validate(id);
        lessonRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<LessonDto> findAll(){
        return lessonRepository.findAllWithRelations(PageRequest.of(0,5, Sort.by("id")))
                .map(e -> new LessonDto(e.getTeacher().getId(),
                            e.getStudents().stream()
                              .map(Student::getId)
                              .collect(Collectors.toSet()),
                            e.getClassroom().getId(),
                            e.getDateTime(),
                            e.getTopic()));
    }

    private void lessonTimeValidation(LessonCommand command, Teacher teacher, Set<Student> students, Classroom classroom) {
        if(teacher.getLessons().stream()
                .anyMatch(t -> t.getDateTime().isBefore(command.getDateTime().plusHours(1))
                        && t.getDateTime().isAfter(command.getDateTime().minusHours(1)))){
            throw new InvalidLessonTimeException("teacher have a lesson in this time");
        }

        if(students.stream()
                .map(Student::getLessons)
                .flatMap(Collection::stream)
                .anyMatch(t -> t.getDateTime().isBefore(command.getDateTime().plusHours(1))
                        && t.getDateTime().isAfter(command.getDateTime().minusHours(1)))){
            throw new InvalidLessonTimeException("some student have a lesson in this time");
        }

        if(classroom.getLessons().stream()
                .anyMatch(t -> t.getDateTime().isBefore(command.getDateTime().plusHours(1))
                        && t.getDateTime().isAfter(command.getDateTime().minusHours(1)))){
            throw new InvalidLessonTimeException("classroom have a lesson in this time");
        }
    }

}
