/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nodeletecascade;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Teodor Todorov
 */
@Service
public class StockService3 {

    @Autowired
    private StockRepository3 repo;

    @Autowired
    private StockDailyRecordRepository3 stockDailyRecorderRepo;

    public List<Stock3> getAllStocks() {
        return repo.findAll();
    }

    public Stock3 deleteStock(Long id) {
        Stock3 tempStock = repo.findOne(id);

        if (tempStock == null) {
            return null;
        }

        stockDailyRecorderRepo.removeByStockStockId(id);
        repo.delete(id);

        return tempStock;
    }

}
