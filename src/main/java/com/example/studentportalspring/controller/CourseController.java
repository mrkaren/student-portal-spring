package com.example.studentportalspring.controller;

import com.example.studentportalspring.model.Course;
import com.example.studentportalspring.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public String courses(ModelMap modelMap) {
        List<Course> courses = courseRepository.findAll();
        modelMap.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/courses/delete")
    public String deleteCourse(@RequestParam("id") int id) {
        courseRepository.deleteById(id);
        return "redirect:/courses";
    }

    @GetMapping("/addCourse")
    public String addCourse() {
        return "addCourse";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("name") String name, @RequestParam("price") double price) {
        Course course = new Course();
        course.setName(name);
        course.setPrice(price);
        courseRepository.save(course);
        return "redirect:/courses";

    }

}
