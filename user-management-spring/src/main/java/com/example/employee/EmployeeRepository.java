/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author msol-pc
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Page<Employee> findByEmployerUserUserName(String employerName, Pageable pageRequest);

    public Employee findByUserUserName(String userName);

    public Employee findByTasksUpdatesUpdaterId(Long id);

    public Employee findByUserUserId(Long id);
}
