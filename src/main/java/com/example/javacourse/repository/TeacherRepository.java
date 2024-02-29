package com.example.javacourse.repository;

import com.example.javacourse.entity.Classroom;
import com.example.javacourse.entity.Teacher;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "SELECT c FROM Teacher c LEFT JOIN FETCH c.lessons", countQuery = "SELECT COUNT(c) FROM Teacher c")
    Page<Teacher> findAllWithLessons(Pageable pageable);

    @Query("SELECT t FROM Teacher t LEFT JOIN FETCH t.lessons WHERE t.id = :teacherId")
    Optional<Teacher> findByIdWithLessons(@Param("teacherId") Long teacherId);

    Optional<Teacher> findByEmail(String email);
}
