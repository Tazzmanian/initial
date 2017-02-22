/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.deletecascade;

import com.example.associations.nodeletecascade.*;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Teodor Todorov
 */
@Repository
public interface StockDailyRecordRepository4 extends JpaRepository<StockDailyRecord4, Long> {

    @Transactional
    public List<StockDailyRecord4> removeByStockStockId(Long id);
}