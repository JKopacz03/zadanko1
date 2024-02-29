package com.example.javacourse.repository;

import com.example.javacourse.entity.Classroom;
import com.example.javacourse.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    @Query(value = "SELECT c FROM Classroom c LEFT JOIN FETCH c.lessons", countQuery = "SELECT COUNT(c) FROM Classroom c")
    Page<Classroom> findAllWithLessons(Pageable pageable);
    //waldek pokazywal ze tutaj jeszcze sie robi projekcje na dto w query, aby nie zapychac pamieci aplikacji
    //ale no mam zrobic mapowanie z entity na dto za pomoca modelMapper wiec to tak zostawie

    @Query(value = "SELECT c FROM Classroom c LEFT JOIN FETCH c.lessons WHERE c.id = :classroomId")
    Optional<Classroom> findByIdWithLessons(@Param("classroomId") long id);

    Optional<Classroom> findByLocation(int i);
}
