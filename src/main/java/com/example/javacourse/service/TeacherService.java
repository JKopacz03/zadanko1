package com.example.javacourse.service;

    import com.example.javacourse.config.ObjectsValidator;
    import com.example.javacourse.entity.Lesson;
    import com.example.javacourse.entity.Teacher;
import com.example.javacourse.entity.commands.TeacherCommand;
    import com.example.javacourse.entity.dto.TeacherDto;
    import com.example.javacourse.excpetions.EntityWithExistingEmailException;
    import com.example.javacourse.excpetions.NotExistingClassroomException;
    import com.example.javacourse.excpetions.NotExistingTeacherException;
    import com.example.javacourse.repository.TeacherRepository;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
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
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ModelMapper modelMapper;
    private final ObjectsValidator<Long> longObjectsValidator;
    private final ObjectsValidator<TeacherCommand> teacherObjectsValidator;

    public void save(TeacherCommand command){
        teacherObjectsValidator.validate(command);

        if(teacherRepository
                .findByEmail(command.getEmail())
                .isPresent()){
            throw new EntityWithExistingEmailException();
        }

        Teacher teacher = modelMapper.map(command, Teacher.class);
        teacher.setGrade(command.getGrade());

        teacherRepository.save(teacher);
    }

    public void remove(@Positive @NotNull long id){
        longObjectsValidator.validate(id);
        if(teacherRepository.findById(id).isEmpty()){
            throw new NotExistingTeacherException(
                    String.format("Teacher with id %d not exist", id));
        }
        teacherRepository.deleteById(id);
    }

    public Teacher findById(@Min(1) @NotNull long id){
        longObjectsValidator.validate(id);
        return teacherRepository.findByIdWithLessons(id)
                .orElseThrow(() -> new NotExistingTeacherException(
                        String.format("Teacher with id %d not exist", id)));
    }

    @Transactional(readOnly = true)
    public Page<TeacherDto> findAll(){
        return teacherRepository.findAllWithLessons(PageRequest.of(0,5, Sort.by("id")))
//                .map(e -> modelMapper.map(e, TeacherDto.class));
                .map(e -> new TeacherDto(e.getName(),
                        e.getLastName(),
                        e.getEmail(),
                        e.getGrade(),
                        e.getRate(),
                        e.getLessons().stream()
                                .map(Lesson::getId).collect(Collectors.toSet())));
    }

}
