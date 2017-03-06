/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.EmployeeDTO;
import java.util.List;
import javax.mail.Quota;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 *
 * @author Teodor Todorov
 */
public class TaskResourceSupport extends ResourceSupport {

    private final Task task;

    public TaskResourceSupport(Task task) {
        this.task = task;
    }

    public TaskResourceSupport setTaskSelfLink(Long taskId) {
        Link selfLink = linkTo(methodOn(TaskController.class).getTask(taskId)).withSelfRel();
        this.add(selfLink);
        return this;
    }

    public Task getTask() {
        return task;
    }
}
