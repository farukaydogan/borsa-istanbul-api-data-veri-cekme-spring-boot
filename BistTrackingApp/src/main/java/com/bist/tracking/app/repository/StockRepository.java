package com.bist.tracking.app.repository;

import com.bist.tracking.app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Integer> {

}
