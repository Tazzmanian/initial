/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import com.example.employee.EmployeeService;
import com.example.employer.Employer;
import com.example.employer.EmployerService;
import com.example.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Teodor Todorov
 */
@RestController
public class TaskController {

    @Autowired
    private TaskService service;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal User user) throws Exception {
        Employer employer = employerService.getByUsername(user.getUsername());
        List<Employee> employees = task.getAssignees();
        for (Employee employee : employees) {
            if (!isOwner(employer, employee)) {
                throw new Exception("This employer cannot give a task to employee No: " + employee.getEmployeeNumber());
            }
        }
        return service.createTask(task);
    }

    @PreAuthorize("this.isAssignee(principal.username, #id)")
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TaskDTO addUpdate(@PathVariable Long id, @RequestBody Update update, @AuthenticationPrincipal User user) throws Exception {
        Employee employee = employeeService.getByUsername(user.getUsername());
        return service.logWork(id, update, employee);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskUpdaterDTO> getTasks(Pageable pageRequest, @AuthenticationPrincipal User user) {
        Employer employer = employerService.getByUsername(user.getUsername());
        List<Employee> employees = employer.getEmployees();
        return service.getEmployeesTasks(employees, pageRequest).getContent();
    }

    public boolean isOwner(Employer employer, Employee employee) {
        if ((employer.getEmployees().contains(employee))) {
            return true;
        }
        return false;
    }

    public boolean isAssignee(String username, Long id) {
        Employee employee = employeeService.getByUsername(username);
        Task task = service.getById(id);
        if ((task.getAssignees().contains(employee))) {
            return true;
        }
        return false;
    }
}
