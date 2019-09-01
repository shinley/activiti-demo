package com.shinley.activiti.controller;

import com.shinley.activiti.business.StockDailyBiz;
import com.shinley.activiti.model.Prediction;
import com.shinley.activiti.model.StockDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StockController {

    @Autowired
    private StockDailyBiz stockDailyBiz;

    @PostMapping("/stock/daily/add")
    public void addStock(@RequestBody StockDaily stockDaily) {
        stockDailyBiz.addStockDaily(stockDaily);
    }

    @GetMapping("/stock/prediction")
    public Prediction findPrediction(@RequestParam("code") String code) {
        return stockDailyBiz.findPrediction(code);
    }

}
