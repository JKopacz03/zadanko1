package com.example.javacourse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE classroom SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@EqualsAndHashCode(of = "id")
@Table(name = "classroom")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private int location;
    @OneToMany(
            mappedBy = "classroom"
    )
    private Set<Lesson> lessons = new HashSet<>();
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

}
