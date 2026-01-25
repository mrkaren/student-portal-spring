package com.example.studentportalspring.repository;

import com.example.studentportalspring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
