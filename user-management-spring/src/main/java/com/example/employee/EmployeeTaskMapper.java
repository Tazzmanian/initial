/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author msol-pc
 */
public class EmployeeTaskMapper {

    public static List<EmployeeTaskDTO> mapEntitiesIntoDTOs(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeTaskMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static EmployeeTaskDTO mapEntityIntoDTO(Employee employee) {

        EmployeeTaskDTO dto = new EmployeeTaskDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setUpdates(employee.getUpdates());
        return dto;
    }

    public static Page<EmployeeTaskDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Employee> source) {
        List<EmployeeTaskDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }
}
