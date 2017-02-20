/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nosaveupdate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Teodor Todorov
 */
@Service
public class StockService {

    @Autowired
    private StockRepository repo;

    @Autowired
    private StockDailyRecordRepository stockDailyRecorderRepo;

    public List<Stock> getAllStocks() {
        return repo.findAll();
    }

    public Stock updateStock(Stock stock, Long id) {
        Stock tempStock = repo.findOne(id);
        tempStock.setStockCode(stock.getStockCode());
        tempStock.setStockName(stock.getStockName());

        for (StockDailyRecord record : stock.getRecords()) {
            record.setStock(tempStock);
        }

        stockDailyRecorderRepo.save(stock.getRecords());

        List<StockDailyRecord> tempRec = tempStock.getRecords();
        tempRec.addAll(stock.getRecords());
        return repo.save(tempStock);
    }

}
