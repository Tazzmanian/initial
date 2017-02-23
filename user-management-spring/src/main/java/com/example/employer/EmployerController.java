/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employer;

import com.example.employee.Employee;
import com.example.employee.EmployeeService;
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
public class EmployerController {

    @Autowired
    private EmployerService service;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public EmployerDTO save(@RequestBody Employer empl) {
        return service.save(empl);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/employers/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<EmployerDTO> getAllEmployersFiltered(Pageable pageRequest) {
        return service.getAllEmployers(pageRequest).getContent();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/employers/active/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Employer activate(@PathVariable Long id) {
        return service.changeActive(id);
    }

    @RequestMapping(value = "/employers/employeecount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Integer getEmployeeCount(@AuthenticationPrincipal User user) {
        Employer empl = service.getByUsername(user.getUsername());
        return empl.getEmployees().size();
    }

    @RequestMapping(value = "/employers/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Employee> getOwnEmployees(Pageable pageRequest, @AuthenticationPrincipal User user) {
        Employer empl = service.getByUsername(user.getUsername());
        return employeeService.getEmployeesByEmployer(empl.getUser().getUsername(), pageRequest).getContent();
    }

    @PreAuthorize("this.isOwner(principal.username, #id)")
    @RequestMapping(value = "/employers/employees/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Employee update(@PathVariable Long id, @RequestBody Employee employee) throws Exception {
        return employeeService.update(id, employee);
    }

    public boolean isOwner(String username, Long id) {
        Employer employer = service.getByUsername(username);
        Employee employee = employeeService.getById(id);
        if ((employer.getEmployees().contains(employee))) {
            return true;
        }
        return false;
    }
}
