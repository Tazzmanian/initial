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
public class TaskResourceSupportDTO extends ResourceSupport {

    private final TaskDTO task;

    public TaskResourceSupportDTO(TaskDTO task) {
        this.task = task;
    }

    public TaskResourceSupportDTO setTaskSelfLink(Long taskId) {
        Link selfLink = linkTo(methodOn(TaskController.class).getTask(taskId)).withSelfRel();
        this.add(selfLink);
        return this;
    }

    public TaskDTO getTask() {
        return task;
    }
}
