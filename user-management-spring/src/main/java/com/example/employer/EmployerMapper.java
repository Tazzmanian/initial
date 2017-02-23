/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employer;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class EmployerMapper {

    public static List<EmployerDTO> mapEntitiesIntoDTOs(List<Employer> employers) {
        return employers.stream()
                .map(EmployerMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static EmployerDTO mapEntityIntoDTO(Employer employer) {

        EmployerDTO dto = new EmployerDTO();
        dto.setId(employer.getId());
        dto.setEmployees(employer.getEmployees());
        return dto;
    }

    public static Page<EmployerDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Employer> source) {
        List<EmployerDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }

}
