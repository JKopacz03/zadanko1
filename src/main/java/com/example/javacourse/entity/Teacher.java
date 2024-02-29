package com.example.javacourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "teacher")
@SQLDelete(sql = "UPDATE teacher SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "grade")
    private Grade grade;
    @Column(name = "rate")
    private double rate;
    @OneToMany(
            mappedBy = "teacher",
            cascade=CascadeType.ALL
    )
    private Set<Lesson> lessons = new HashSet<>();
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public void setGrade(String grade) {
        this.grade = Grade.valueOf(grade);
    }

    public void addLessons(Set<Lesson> lessons){
        this.lessons.addAll(lessons);
    }

}
