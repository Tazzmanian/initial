/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 *
 * @author msol-pc
 */
@Entity(name = "T_UPDATE")
@Data
public class Update {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "UPDATE_ID")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeUpdated;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "UPDATE_UPDATER", joinColumns = {
        @JoinColumn(name = "UPDATER_ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "UPDATE_ID")})
    @JsonIgnore
    private Employee updater;

    private String body;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TASK_UPDATE", joinColumns = {
        @JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "UPDATE_ID")})
    @JsonBackReference
    private Task task;

    @PrePersist
    void setDates() {
        this.timeUpdated = new Date();
    }
}
