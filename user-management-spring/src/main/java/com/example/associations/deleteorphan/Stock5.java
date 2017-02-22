/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.deleteorphan;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity(name = "STOCK5")
@Data
public class Stock5 implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    private Long stockId;

    @Column(name = "CODE")
    private String stockCode;
    @Column(name = "STOCK_NAME")
    private String stockName;

    @OneToMany(mappedBy = "stock", orphanRemoval = true)
    //@JsonIgnore
    private List<StockDailyRecord5> records;

    //getter, setter and constructor
}
