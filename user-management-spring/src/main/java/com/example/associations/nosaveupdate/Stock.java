/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nosaveupdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity(name = "STOCK")
@Data
public class Stock implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    private Integer stockId;

    @Column(name = "CODE")
    private String stockCode;
    @Column(name = "STOCK_NAME")
    private String stockName;

    //getter, setter and constructor
}
