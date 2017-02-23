/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employer;

import com.example.employee.Employee;
import java.util.List;
import lombok.Data;

@Data
public class EmployerDTO {

    private Long id;
    private List<Employee> employees;
}
