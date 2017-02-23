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

public class EmployeeMapper {

    public static List<EmployeeDTO> mapEntitiesIntoDTOs(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static EmployeeDTO mapEntityIntoDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setFirstName(employee.getFirstName());
        dto.setMiddleName(employee.getMiddleName());
        dto.setLastName(employee.getLastName());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setSex(employee.getSex());
        dto.setDob(employee.getDob());

        return dto;
    }

    public static Page<EmployeeDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Employee> source) {
        List<EmployeeDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }

}
