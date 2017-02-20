/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import com.example.user.User;
import com.example.user.UserRepository;
import com.example.user.UserService;
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

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    public Employee save(Employee empl) {
        User user = empl.getUser();
        userService.saveUser(user);

        user.setBirthDate(empl.getDob());
        user.setFirstName(empl.getFirstName());
        user.setLastName(empl.getLastName());
        user.setPhoneNumber(empl.getPhoneNumber());

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
        return repo.save(dbEmpl);
    }

    public EmployeeDTO getEmployeeDTOByUserName(String userName) {
        User user = userRepo.findByUserName(userName);
        Employee empl = repo.findByUserUserName(userName);

        // null fields for embeded data
        if (empl.getDob() == null) {
            empl.setDob(user.getBirthDate());
        }
        if (empl.getFirstName() == null) {
            empl.setFirstName(user.getFirstName());
        }
        if (empl.getLastName() == null) {
            empl.setLastName(user.getLastName());
        }
        if (empl.getPhoneNumber() == null) {
            empl.setPhoneNumber(user.getPhoneNumber());
        }

        return EmployeeMapper.mapEntityIntoDTO(repo.save(empl));
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
