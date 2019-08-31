package com.shinley.activiti.business;

import com.shinley.activiti.dao.StockDailyDao;
import com.shinley.activiti.model.StockDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockDailyBiz {

    @Autowired
    private StockDailyDao stockDailyDao;

    public void addStockDaily(StockDaily stockDaily) {

        // 添加到每日股票数据表中
        stockDailyDao.insert(stockDaily);
        // 计算次日股票数据
        String avgPrice = stockDaily.getAvgPrice();
        String lowPrice = stockDaily.getLowPrice();
        String heightPrice = stockDaily.getHeightPrice();
        String openPrice = stockDaily.getOpenPrice();
        String closePrice = stockDaily.getClosePrice();
        BigDecimal avgBigDecimal = new BigDecimal(avgPrice);
        BigDecimal lowBigDecimal = new BigDecimal(lowPrice);
        BigDecimal heighBigDecimal = new BigDecimal(heightPrice);
        BigDecimal openBigDecimal = new BigDecimal(openPrice);
        BigDecimal closeBigDecimal = new BigDecimal(closePrice);

        BigDecimal heighAndLow = heighBigDecimal.add(lowBigDecimal);
        BigDecimal two = new BigDecimal("2");
        BigDecimal doubleClose =closeBigDecimal.multiply(two);

    }

    /**
     * 获取第二天的最高价
     * @return
     */
    private BigDecimal getNextHeighPrice(BigDecimal avgPrice, BigDecimal heighPrice, BigDecimal lowPrice) {
        return avgPrice.add(heighPrice).subtract(lowPrice);
    }

    /**
     * 获取第二天次高价
     * @return
     */
    private BigDecimal getNextSecondHeighPrice(BigDecimal avgPrice, BigDecimal lowPrice) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal doubleAvg = avgPrice.multiply(two);
        return doubleAvg.subtract(lowPrice);
    }

    /**
     * 获取第二天次低价
     */
    private BigDecimal getNextSecondLowPrice(BigDecimal avgPrice, BigDecimal heighPrice) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal doubleAvg = avgPrice.multiply(two);
        return doubleAvg.subtract(heighPrice);
    }

    /**
     * 获取第二天最低
     *
     * @return
     */
    private BigDecimal getNextLowestPrice(BigDecimal avgPrice, BigDecimal heighPrice, BigDecimal lowPrice ) {
        return avgPrice.subtract(heighPrice).add(lowPrice);
    }

}
