/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.saveupdatecascade;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Teodor Todorov
 */
@Service
public class StockService1 {

    @Autowired
    private StockRepository1 repo;

    public List<Stock1> getAllStocks() {
        return repo.findAll();
    }

    public Stock1 updateStock(Stock1 stock, Long id) {
        Stock1 tempStock = repo.findOne(id);
        tempStock.setStockCode(stock.getStockCode());
        tempStock.setStockName(stock.getStockName());

        for (StockDailyRecord1 record : stock.getRecords()) {
            record.setStock(tempStock);
        }

        List<StockDailyRecord1> tempRec = tempStock.getRecords();
        tempRec.addAll(stock.getRecords());
        return repo.save(tempStock);
    }

}
