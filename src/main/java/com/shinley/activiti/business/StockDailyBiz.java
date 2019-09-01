package com.shinley.activiti.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shinley.activiti.dao.PredictionDao;
import com.shinley.activiti.dao.StockDailyDao;
import com.shinley.activiti.model.Prediction;
import com.shinley.activiti.model.StockDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class StockDailyBiz {

    @Autowired
    private StockDailyDao stockDailyDao;

    @Autowired
    private PredictionDao predictionDao;

    public void addStockDaily(StockDaily stockDaily) {

        // 添加到每日股票数据表中
        stockDailyDao.insert(stockDaily);
        // 计算次日股票数据
        String avgPrice = stockDaily.getAvgPrice();
        String lowPrice = stockDaily.getLowPrice();
        String heighPrice = stockDaily.getHeighPrice();
        BigDecimal avgBigDecimal = new BigDecimal(avgPrice);
        BigDecimal lowBigDecimal = new BigDecimal(lowPrice);
        BigDecimal heighBigDecimal = new BigDecimal(heighPrice);

        BigDecimal nextHeighPrice = this.getNextHeighPrice(avgBigDecimal, heighBigDecimal, lowBigDecimal);
        BigDecimal nextSecondHeighPrice = this.getNextSecondHeighPrice(avgBigDecimal, lowBigDecimal);
        BigDecimal nextSecondLowPrice = this.getNextSecondLowPrice(avgBigDecimal, heighBigDecimal);
        BigDecimal nextLowestPrice = this.getNextLowestPrice(avgBigDecimal, heighBigDecimal, lowBigDecimal);

        LocalDate date = stockDaily.getDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String day = dayOfWeek.toString();
        LocalDate nextDay = date.plusDays(1);
        if ("FRIDAY".equalsIgnoreCase(day)) {
            nextDay = date.plusDays(3);
        }

        Prediction prediction = new Prediction();
        prediction.setCode(stockDaily.getCode());
        prediction.setCode(stockDaily.getCode());
        prediction.setDate(nextDay);
        prediction.setHighestPrice(nextHeighPrice.toString());
        prediction.setLowestPrice(nextLowestPrice.toString());
        prediction.setSecondHighPrice(nextSecondHeighPrice.toString());
        prediction.setSecondLowPrice(nextSecondLowPrice.toString());
        predictionDao.insert(prediction);
    }

    /**
     * 查询预测数据
     *
     * @return
     */
    public Prediction findPrediction(String code) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();
        LocalTime time = LocalTime.of(15, 10);
        LocalDateTime afternoon1510 = LocalDateTime.of(nowDate, time);

        QueryWrapper<Prediction> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        // 如果在15:10以前, 查询当天的,
        if (now.isBefore(afternoon1510)) {
            queryWrapper.eq("date", nowDate);
        }
        // 如果在15:10以后,要是询第二天的数据
        if (now.isAfter(afternoon1510)) {
            LocalDate nextDay = nowDate.plusDays(1);
            queryWrapper.eq("date", nextDay);
        }
        Prediction prediction = predictionDao.selectOne(queryWrapper);
        return prediction;
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
