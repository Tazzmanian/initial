/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.EmployeeDTO;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 *
 * @author Teodor Todorov
 */
public class EmployeesResourceSupportDTO extends ResourceSupport {

    private final List<EmployeeDTO> assignees;

    public EmployeesResourceSupportDTO(List<EmployeeDTO> assignees) {
        this.assignees = assignees;
    }

    public EmployeesResourceSupportDTO setSelfLink(String servletPath) {
        Link selfLink = linkTo(TaskController.class).slash(servletPath).withSelfRel();
        this.add(selfLink);
        return this;
    }

    public List<EmployeeDTO> getAssignees() {
        return assignees;
    }

}
