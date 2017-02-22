/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.deleteorphan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Teodor Todorov
 */
@RestController
public class StockController5 {

    @Autowired
    private StockService5 service;

    @RequestMapping(value = "/stocks5", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Stock5> getAllStocks() {
        return service.getAllStocks();
    }

    @RequestMapping(value = "/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<StockDailyRecord5> getAllRecords() {
        return service.getAllRecords();
    }

    @RequestMapping(value = "/stocksdailyrecords/{stock_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Stock5 removeRecords(@PathVariable Long stock_id) {
        return service.deleteStock(stock_id);
    }

    @RequestMapping(value = "/stocks5/tests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String printTest() {
        return "Test";
    }
}
