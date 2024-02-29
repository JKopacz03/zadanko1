package com.example.javacourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE lesson SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EqualsAndHashCode(of = "id")
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "teacher_id"
    )
    private Teacher teacher;
    @ManyToMany(
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
            name = "students_lessons",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();
    @ManyToOne(
            cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "classroom_id"
    )
    private Classroom classroom;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "topic")
    private String topic;
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

}
