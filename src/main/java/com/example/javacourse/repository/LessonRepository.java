package com.example.javacourse.repository;

import com.example.javacourse.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value = "SELECT c FROM Lesson c LEFT JOIN FETCH c.students " +
            "LEFT JOIN FETCH c.teacher " +
            "LEFT JOIN FETCH c.classroom",
            countQuery = "SELECT COUNT(c) FROM Lesson c")
    Page<Lesson> findAllWithRelations(Pageable pageable);
}
