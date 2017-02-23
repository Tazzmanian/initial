/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import com.example.employer.Employer;
import com.example.employer.EmployerService;
import com.example.task.TaskDTO;
import com.example.task.TaskService;
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
 * @author msol-pc
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/employees", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Employee save(@RequestBody Employee empl) {
        return service.save(empl);
    }

    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<EmployeeDTO> getAllEmployees(Pageable pageRequest) {
        return service.getAllEmployees(pageRequest).getContent();
    }

    @PreAuthorize("this.isOwner(principal.username, #id)")
    @RequestMapping(value = "/employees/active/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Employee activate(@PathVariable Long id) {
        return service.changeActive(id);
    }

    @RequestMapping(value = "/employees/self", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public EmployeeDTO currentEmployee(@AuthenticationPrincipal User user) {
        return service.getEmployeeDTOByUserName(user.getUsername());
    }

    @RequestMapping(value = "/employees/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TaskDTO> getTasks(Pageable pageRequest, @AuthenticationPrincipal User user) {
        Employee empl = service.getByUsername(user.getUsername());
        return taskService.getByEmployeeId(empl.getId(), pageRequest).getContent();
    }

    @RequestMapping(value = "/employees", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public EmployeeDTO update(@RequestBody EmployeeDTO empl, @AuthenticationPrincipal User user) throws Exception {
        return service.update(user.getUsername(), empl);
    }

    @RequestMapping(value = "/employees/tasked", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeTaskDTO> getTaskedEmployees() {
        return service.getAllTasked();
    }

    public boolean isOwner(String username, Long id) {
        Employer employer = employerService.getByUsername(username);
        Employee employee = service.getById(id);
        if ((employer.getEmployees().contains(employee))) {
            return true;
        }
        return false;
    }

}
