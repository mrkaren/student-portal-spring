package com.example.studentportalspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSearchCriteria {

    private String name;
    private String surname;
    private String email;

}
