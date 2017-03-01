/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import javax.mail.Quota;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author Teodor Todorov
 */
public class TaskResourceSupport extends ResourceSupport {

    private final Task task;

    public TaskResourceSupport(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
