package com.example.studentportalspring.controller;

import com.example.studentportalspring.model.Student;
import com.example.studentportalspring.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public String students(ModelMap modelMap) {
        List<Student> all = studentRepository.findAll();
        modelMap.addAttribute("students", all);

        return "students";
    }

}
