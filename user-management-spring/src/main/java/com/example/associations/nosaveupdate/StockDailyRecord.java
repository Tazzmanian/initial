/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nosaveupdate;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "STOCK_DAILY_RECORD")
@Data
public class StockDailyRecord implements java.io.Serializable {

    @Id
    @Column(name = "ID")
    private Integer recordId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    private Float priceOpen;
    private Float priceClose;
    private Float priceChange;

    @Column(name = "VOLUME")
    private Long volume;
    private Date date;

    //getter, setter and constructor
}
