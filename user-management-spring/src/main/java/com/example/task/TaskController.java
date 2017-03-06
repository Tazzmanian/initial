/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import com.example.employee.EmployeeDTO;
import com.example.employee.EmployeeService;
import com.example.employer.Employer;
import com.example.employer.EmployerService;
import com.example.user.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public TaskResourceSupport createTask(@RequestBody Task task, @AuthenticationPrincipal User user) throws Exception {
        Employer employer = employerService.getByUsername(user.getUsername());
        List<Employee> employees = task.getAssignees();
        for (Employee employee : employees) {
            if (!isOwner(employer, employee)) {
                throw new Exception("This employer cannot give a task to employee No: " + employee.getEmployeeNumber());
            }
        }
        return service.createTaskResourceSupport(task);
    }

    @PreAuthorize("this.isAssignee(principal.username, #id)")
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TaskResourceSupportDTO addUpdate(@PathVariable Long id, @RequestBody Update update, @AuthenticationPrincipal User user) throws Exception {
        Employee employee = employeeService.getByUsername(user.getUsername());
        return service.logWorkResourceSupport(id, update, employee);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TaskResourceSupportUpdaterDTO> getTasks(Pageable pageRequest, @AuthenticationPrincipal User user) {
        Employer employer = employerService.getByUsername(user.getUsername());
        List<Employee> employees = employer.getEmployees();
        return service.getEmployeesResourceSupportTasks(employees, pageRequest).getContent();
    }

    //- добавяш изтриване на task по id – само от employer и само негови таскове!
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Task deleteTask(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return service.delete(user.getUsername(), id);
    }

    //- добавяш показване само на assignees (input task id, employer id) 
    @RequestMapping(value = "/tasks/assignees/{taskId}/{employerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public EmployeesResourceSupportDTO showAssigneesOnTaskByEmployer(@PathVariable Long taskId, @PathVariable Long employerId) {
        EmployeesResourceSupportDTO listLink = new EmployeesResourceSupportDTO(service.showAssigneesOnTaskByEmployer(taskId, employerId));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        listLink.setSelfLink(request.getServletPath());
        return listLink;
    }

    // - добавяш показване само на updates (input task id, employer id)
    @RequestMapping(value = "/tasks/updates/{taskId}/{employerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public UpdatesResourceSupportDTO showTasksUpdatesByEmployer(@PathVariable Long taskId, @PathVariable Long employerId) {
        UpdatesResourceSupportDTO updateLink = new UpdatesResourceSupportDTO(service.showTasksUpdatesByEmployer(taskId, employerId));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        updateLink.setSelfLink(request.getServletPath());
        return updateLink;
    }

    // test
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TaskResourceSupport getTask(@PathVariable Long id) {
        Task task = service.getTaskById(id);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Link selfLink = linkTo(TaskController.class).slash(request.getServletPath()).withSelfRel();
        TaskResourceSupport taskLink = new TaskResourceSupport(task);
        taskLink.add(selfLink);

        return taskLink;
    }

    public boolean isOwner(Employer employer, Employee employee) {
        for (Employee empl : employer.getEmployees()) {
            if (empl.getId().equals(employee.getId())) {
                return true;
            }
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
