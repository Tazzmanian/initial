/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.employee;

import com.example.task.Update;
import java.util.List;
import lombok.Data;

/**
 *
 * @author msol-pc
 */
@Data
public class EmployeeTaskDTO {

    private String firstName;
    private List<Update> updates;
}
