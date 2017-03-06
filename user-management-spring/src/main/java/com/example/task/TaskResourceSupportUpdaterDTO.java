/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author Teodor Todorov
 */
public class TaskResourceSupportUpdaterDTO extends ResourceSupport {

    private final TaskUpdaterDTO task;

    public TaskResourceSupportUpdaterDTO(TaskUpdaterDTO dto) {
        this.task = dto;
    }

    public TaskResourceSupportUpdaterDTO setTaskSelfLink(Long taskId) {
        Link selfLink = linkTo(methodOn(TaskController.class).getTask(taskId)).withSelfRel();
        this.add(selfLink);
        return this;
    }

    public TaskUpdaterDTO getTask() {
        return task;
    }
}
