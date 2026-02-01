package com.example.studentportalspring.service.impl;

import com.example.studentportalspring.model.Course;
import com.example.studentportalspring.model.Skill;
import com.example.studentportalspring.model.Student;
import com.example.studentportalspring.repository.StudentRepository;
import com.example.studentportalspring.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Value("${student.portal.upload.image.directory.path}")
    private String imageDirectoryPath;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student save(Student student, MultipartFile multipartFile) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageDirectoryPath + fileName);
            try {
                multipartFile.transferTo(file);
                student.setPictureName(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return studentRepository.save(student);
    }

    @Override
    public List<Student> findByCourse(Course course) {
        return studentRepository.findByCourse(course);
    }

    @Override
    public List<Student> findBySkill(Skill skill) {
        return studentRepository.findBySkill(skill);
    }
}
