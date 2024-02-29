package com.example.javacourse.repository;

import com.example.javacourse.entity.Student;
import com.example.javacourse.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT c FROM Student c LEFT JOIN FETCH c.lessons", countQuery = "SELECT COUNT(c) FROM Student c")
    Page<Student> findAllWithLessons(Pageable pageable);

    @Query(value = "SELECT c FROM Student c LEFT JOIN FETCH c.lessons WHERE c.id IN :studentIds")
    Iterable<Student> findAllByIdWithLessons(@Param("studentIds") Set<Long> students);

    Optional<Student> findByEmail(String email);
}
