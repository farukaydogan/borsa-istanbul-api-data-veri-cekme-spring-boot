package com.bist.tracking.app.service;

import com.bist.tracking.app.model.Stock;
import com.bist.tracking.app.repository.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

}
