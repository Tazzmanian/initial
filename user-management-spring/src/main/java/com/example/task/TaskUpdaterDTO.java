/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import com.example.employee.Employee;
import lombok.Data;

/**
 *
 * @author msol-pc
 */
@Data
public class TaskUpdaterDTO {

    private String title;
    private Employee lastUpdated;

}
