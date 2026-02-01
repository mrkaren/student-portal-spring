package com.example.studentportalspring.controller;

import com.example.studentportalspring.model.Course;
import com.example.studentportalspring.model.Skill;
import com.example.studentportalspring.model.Student;
import com.example.studentportalspring.service.CourseService;
import com.example.studentportalspring.service.SkillService;
import com.example.studentportalspring.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final SkillService skillService;
    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/students")
    public String students(ModelMap modelMap,
                           @RequestParam(value = "skillId", required = false) Integer skillId,
                           @RequestParam(value = "courseid", required = false) Integer courseId) {
        List<Student> result;
        if (skillId != null) {
            Skill skill = skillService.findById(skillId);
            result = studentService.findBySkill(skill);
        } else if (courseId != null) {
            Course course = courseService.findById(courseId);
            result = studentService.findByCourse(course);
        } else {
            result = studentService.findAll();

        }
        modelMap.addAttribute("students", result);

        return "students";
    }

    @GetMapping("/students/add")
    public String addStudentPage(ModelMap modelMap) {
        modelMap.addAttribute("courses", courseService.findAll());
        modelMap.addAttribute("skills", skillService.findAll());
        return "addStudent";
    }


    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student,
                             @RequestParam("pic") MultipartFile multipartFile) {
        studentService.save(student, multipartFile);
        return "redirect:/students";
    }



}
