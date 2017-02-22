/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.associations.nocascadedeleteorphan;

import com.example.associations.nosaveupdate.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Teodor Todorov
 */
@Repository
public interface StockRepository5 extends JpaRepository<Stock5, Long> {

}
