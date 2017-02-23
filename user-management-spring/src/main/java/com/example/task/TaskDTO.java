/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.task;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author msol-pc
 */
@Data
public class TaskDTO {

    private String title;
    private Date created;
    private List<Update> updates;

}
