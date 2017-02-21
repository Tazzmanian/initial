/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.saveupdatecascade;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Teodor Todorov
 */
@RestController
public class StockController1 {

    @Autowired
    private StockService1 service;

    @RequestMapping(value = "/stocks/saveupdate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Stock1> getAllStocks() {
        return service.getAllStocks();
    }

//    {
//"stockId": 6,
//"stockCode": "6",
//"stockName": "test6"
//}
    @RequestMapping(value = "/stocks/saveupdate/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Stock1 updateStock(@RequestBody Stock1 stock, @PathVariable Long id) {
        return service.updateStock(stock, id);
    }

    @RequestMapping(value = "/stocks/saveupdate/tests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public String printTest() {
        return "Test";
    }
}
