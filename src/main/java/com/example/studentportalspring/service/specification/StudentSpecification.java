package com.example.studentportalspring.service.specification;

import com.example.studentportalspring.dto.StudentSearchCriteria;
import com.example.studentportalspring.model.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification implements Specification<Student> {

    private StudentSearchCriteria searchCriteria;

    public StudentSpecification(StudentSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Path<String> name = root.get("name");
        Path<String> surname = root.get("surname");
        Path<String> email = root.get("email");

        final List<Predicate> predicates = new ArrayList<Predicate>();

        if(searchCriteria.getName() != null && !searchCriteria.getName().isBlank()) {
            predicates.add(criteriaBuilder.like(name, "%" + searchCriteria.getName() + "%"));
        }

        if(searchCriteria.getSurname() != null && !searchCriteria.getSurname().isBlank()) {
            predicates.add(criteriaBuilder.like(surname, "%" + searchCriteria.getSurname() + "%"));
        }

        if(searchCriteria.getEmail() != null && !searchCriteria.getEmail().isBlank()) {
            predicates.add(criteriaBuilder.like(email, "%" + searchCriteria.getEmail() + "%"));
        }





        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
