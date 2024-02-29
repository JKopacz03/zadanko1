package com.example.javacourse.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EqualsAndHashCode(of = "id")
@Table(name = "student")
public class Student
{
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
    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;
    @ManyToMany(
            mappedBy = "students"
    )
    private Set<Lesson> lessons = new HashSet<>();
    @Column(name = "registration_date")
    private LocalDate registrationDate = LocalDate.now();
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public void setGrade(String grade) {
        this.grade = Grade.valueOf(grade);
    }

}
