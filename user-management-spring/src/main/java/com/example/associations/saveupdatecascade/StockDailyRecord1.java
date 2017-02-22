/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.saveupdatecascade;

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
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "STOCK_DAILY_RECORD1")
@Data
public class StockDailyRecord1 implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recordId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "STOCK_ID")
    @JsonIgnore
    private Stock1 stock;

    private Float priceOpen;
    private Float priceClose;
    private Float priceChange;

    @Column(name = "VOLUME")
    private Long volume;
    private Date date;

    //getter, setter and constructor
}
