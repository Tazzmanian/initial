/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import com.example.employer.Employer;
import com.example.task.Task;
import com.example.task.Update;
import com.example.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author msol-pc
 */
@Entity(name = "EMPLOYEE")
@Data
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    private String employeeNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Long workDeptId;
    private String phoneNumber;
    private Date hireDate;
    private String job;
    private Integer educationLvl;
    private String sex;
    private Date dob;
    private BigDecimal salary;
    private BigDecimal bonus;
    private BigDecimal commission;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_EMPLOYER", joinColumns = {
        @JoinColumn(name = "EMPLOYEE_ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "EMPLOYER_ID")})
    @JsonIgnore
    private Employer employer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_TASKS", joinColumns = {
        @JoinColumn(name = "EMPLOYEE_ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "TASK_ID")})
    @JsonManagedReference
    private List<Task> tasks;

    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Update> updates;

    public Employer getEmployer() {
        return employer;
    }
}
