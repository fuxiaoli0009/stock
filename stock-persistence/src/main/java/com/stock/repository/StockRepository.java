package com.stock.repository;

import com.stock.dataobject.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockInfo, Integer> {

    public StockInfo findByCode(String code);
}
