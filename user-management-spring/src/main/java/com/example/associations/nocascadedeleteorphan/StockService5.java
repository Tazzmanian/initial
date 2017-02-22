/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nocascadedeleteorphan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Teodor Todorov
 */
@Service
public class StockService5 {
    
    @Autowired
    private StockRepository5 repo;
    
    @Autowired
    private StockDailyRecordRepository5 stockDailyRecorderRepo;
    
    public List<Stock5> getAllStocks() {
        return repo.findAll();
    }
    
    List<StockDailyRecord5> getAllRecords() {
        return stockDailyRecorderRepo.findAll();
    }
    
    Stock5 deleteStock(Long id) {
        Stock5 tempStock = repo.findOne(id);
        
        if (tempStock == null) {
            return null;
        }

        //stockDailyRecorderRepo.removeByStockStockId(id);
        repo.delete(id);

        //tempStock.setRecords(null);
        //repo.save(tempStock);
        return tempStock;
    }
    
}
