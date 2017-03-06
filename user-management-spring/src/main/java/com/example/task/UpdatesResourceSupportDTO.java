/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 *
 * @author Teodor Todorov
 */
public class UpdatesResourceSupportDTO extends ResourceSupport {

    private final List<Update> updates;

    public UpdatesResourceSupportDTO(List<Update> update) {
        this.updates = update;
    }

    public UpdatesResourceSupportDTO setSelfLink(String servletPath) {
        Link selfLink = linkTo(TaskController.class).slash(servletPath).withSelfRel();
        this.add(selfLink);
        return this;
    }

    public List<Update> getAssignees() {
        return updates;
    }

}
