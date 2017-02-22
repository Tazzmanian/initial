/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.deletecascade;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Teodor Todorov
 */
@Service
public class StockService4 {

    @Autowired
    private StockRepository4 repo;

    @Autowired
    private StockDailyRecordRepository4 stockDailyRecorderRepo;

    public List<Stock4> getAllStocks() {
        return repo.findAll();
    }

    public Stock4 deleteStock(Long id) {
        Stock4 tempStock = repo.findOne(id);

        if (tempStock == null) {
            return null;
        }

        //stockDailyRecorderRepo.removeByStockStockId(id);
        repo.delete(id);

        return tempStock;
    }

}
