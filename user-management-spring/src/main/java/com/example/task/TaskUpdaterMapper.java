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
public class TaskUpdaterMapper {

    public static List<TaskUpdaterDTO> mapEntitiesIntoDTOs(List<Task> tasks) {
        return tasks.stream()
                .map(TaskUpdaterMapper::mapEntityIntoDTO)
                .collect(Collectors.toList());
    }

    public static TaskUpdaterDTO mapEntityIntoDTO(Task task) {

        TaskUpdaterDTO dto = new TaskUpdaterDTO();
        dto.setTitle(task.getTitle());
        dto.setLastUpdated(task.getLastUpdated());
        return dto;
    }

    public static Page<TaskUpdaterDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Task> source) {
        List<TaskUpdaterDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(dtos, page, source.getTotalElements());
    }

}
