package com.example.studentportalspring.controller;

import com.example.studentportalspring.model.Course;
import com.example.studentportalspring.model.Skill;
import com.example.studentportalspring.model.Student;
import com.example.studentportalspring.repository.CourseRepository;
import com.example.studentportalspring.repository.SkillRepository;
import com.example.studentportalspring.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final SkillRepository skillRepository;
    private final CourseRepository courseRepository;

    @GetMapping("/students")
    public String students(ModelMap modelMap,
                           @RequestParam(value = "skillId", required = false) Integer skillId,
                           @RequestParam(value = "courseid", required = false) Integer courseid) {
        List<Student> result;
        if (skillId != null) {
            Skill skill = skillRepository.getOne(skillId);
            result = studentRepository.findBySkills(skill);
        } else if (courseid != null) {
            Course course = courseRepository.getOne(courseid);
            result = studentRepository.findByCourse(course);
        } else {
            result = studentRepository.findAll();

        }
        modelMap.addAttribute("students", result);

        return "students";
    }

    @GetMapping("/students/add")
    public String addStudentPage(ModelMap modelMap) {
        modelMap.addAttribute("courses", courseRepository.findAll());
        modelMap.addAttribute("skills", skillRepository.findAll());
        return "addStudent";
    }


    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }



}
