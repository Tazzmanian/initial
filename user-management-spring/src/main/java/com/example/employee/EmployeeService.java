/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author msol-pc
 */
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository repo;
    
    public Employee save(Employee empl) {
        return repo.save(empl);
    }
    
    public Employee getById(Long id) {
        return repo.findOne(id);
    }
    
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }
    
    public Employee getByUsername(String username) {
        return repo.findByUserUserName(username);
    }
    
    public Page<EmployeeDTO> getAllEmployees(Pageable pageRequest) {
        Page<Employee> resultPage = repo.findAll(pageRequest);
        return EmployeeMapper.mapEntityPageIntoDTOPage(pageRequest, resultPage);
    }
    
    public Page<Employee> getEmployeesByEmployer(String employerName, Pageable pageRequest) {
        return repo.findByEmployerUserUserName(employerName, pageRequest);
    }
    
    public Employee update(Long id, Employee empl) throws Exception {
        Employee dbEmpl = repo.findOne(id);
        if (empl == null) {
            throw new Exception("Employee not found");
        }
        dbEmpl.setWorkDeptId(empl.getWorkDeptId());
        dbEmpl.setJob(empl.getJob());
        dbEmpl.setSalary(empl.getSalary());
        dbEmpl.setBonus(empl.getBonus());
        dbEmpl.setCommission(empl.getCommission());
        dbEmpl.setDob(empl.getDob());
        return repo.save(dbEmpl);
    }
    
    public EmployeeDTO getEmployeeDTOByUserName(String userName) {
        Employee empl = repo.findByUserUserName(userName);
        return EmployeeMapper.mapEntityIntoDTO(empl);
    }
    
    public List<EmployeeTaskDTO> getAllTasked() {
        List<Employee> employees = repo.findAll();
        return EmployeeTaskMapper.mapEntitiesIntoDTOs(employees);
    }
    
    public EmployeeDTO update(String userName, EmployeeDTO employee) throws Exception {
        Employee empl = repo.findByUserUserName(userName);
        if (empl == null) {
            throw new Exception("User not found");
        }
        
        empl.setFirstName(employee.getFirstName());
        empl.setMiddleName(employee.getMiddleName());
        empl.setLastName(employee.getLastName());
        empl.setDob(employee.getDob());
        empl.setSex(employee.getSex());
        empl.setPhoneNumber(employee.getPhoneNumber());
        
        repo.save(empl);
        return getEmployeeDTOByUserName(userName);
    }
    
    public Employee changeActive(Long id) {
        Employee empl = repo.findOne(id);
        empl.getUser().setEnabled(!empl.getUser().isEnabled());
        repo.save(empl);
        return empl;
    }
    
}
