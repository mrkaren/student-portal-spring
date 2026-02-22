package com.example.studentportalspring.controller;

import com.example.studentportalspring.dto.StudentSearchCriteria;
import com.example.studentportalspring.model.Student;
import com.example.studentportalspring.service.CourseService;
import com.example.studentportalspring.service.SkillService;
import com.example.studentportalspring.service.StudentService;
import com.example.studentportalspring.service.specification.StudentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final SkillService skillService;
    private final CourseService courseService;
    private final StudentService studentService;

    @GetMapping("/students")
    public String students(ModelMap modelMap,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @ModelAttribute StudentSearchCriteria searchCriteria
    ) {
        if (searchCriteria.getName() == null && searchCriteria.getSurname() == null && searchCriteria.getEmail() == null) {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);
            Sort sort = Sort.by(Sort.Direction.DESC, "id");

            PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, sort);

            Page<Student> result = studentService.findAll(pageRequest);

            int totalPages = result.getTotalPages();

            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .toList();
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }

            modelMap.addAttribute("students", result);
        } else {
            StudentSpecification studentSpecification = new StudentSpecification(searchCriteria);
            Page<Student> result = studentService.findAllWithSpecification(studentSpecification);
            modelMap.addAttribute("students", result);
            modelMap.addAttribute("searchCriteria", searchCriteria);

        }


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
