/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author msol-pc
 */
public class TaskMapper {

    public static List<TaskDTO> mapEntitiesIntoDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static TaskDTO mapEntityIntoDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setUpdates(task.getUpdates());
        dto.setCreated(task.getCreated());
        return dto;
    }

    public static Page<TaskDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Task> source) {
        List<TaskDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }

    //ResourceSupport
    public static List<TaskResourceSupportDTO> mapEntitiesIntoResourceSupportDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(TaskMapper::mapEntityIntoResourceSupportDTO)
                .collect(Collectors.toList());
    }

    public static TaskResourceSupportDTO mapEntityIntoResourceSupportDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setUpdates(task.getUpdates());
        dto.setCreated(task.getCreated());
        TaskResourceSupportDTO resourceDTO = new TaskResourceSupportDTO(dto);
        resourceDTO.setTaskSelfLink(task.getId());
        return resourceDTO;
    }

    public static Page<TaskResourceSupportDTO> mapEntityPageIntoResourceSupportDTOPage(Pageable page, Page<Task> source) {
        List<TaskResourceSupportDTO> dtos = mapEntitiesIntoResourceSupportDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }

}
