package com.shinley.activiti.business;

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
import java.time.format.DateTimeFormatter;

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

        LocalDate date = stockDaily.getStockDate();
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String day = dayOfWeek.toString();
        LocalDate nextDay = date.plusDays(1);
        if ("FRIDAY".equalsIgnoreCase(day)) {
            nextDay = date.plusDays(3);
        }

        Prediction prediction = new Prediction();
        prediction.setCode(stockDaily.getCode());
        prediction.setName(stockDaily.getName());
        prediction.setStockDate(nextDay);
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

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = LocalDate.now();
        LocalTime time = LocalTime.of(15, 10);
        LocalDateTime afternoon1510 = LocalDateTime.of(nowDate, time);

        DayOfWeek dayOfWeek = now.getDayOfWeek();
        String day = dayOfWeek.toString();

        LocalDate nextDay =null;

        // 如果在15:10以前, 查询当天的,
        if (now.isBefore(afternoon1510)) {
            nextDay = nowDate;
        }

        // 如果在15:10以后,要是询第二天的数据
        if (now.isAfter(afternoon1510)) {
            nextDay = nowDate.plusDays(1);
            if (DayOfWeek.FRIDAY.name().equalsIgnoreCase(day)) {
                nextDay = nowDate.plusDays(3);
            }
            if (DayOfWeek.SATURDAY.name().equalsIgnoreCase(day)) {
                nextDay = nowDate.plusDays(2);
            }
        }

        Prediction prediction = predictionDao.findPredictionByCodeDate(code, nextDay);
        return prediction;
    }

    /**
     * 自助预估
     * @return
     */
    public Prediction selfPrediction(StockDaily stockDaily) {
        String lowPrice = stockDaily.getLowPrice();
        String heighPrice = stockDaily.getHeighPrice();
        String closePrice = stockDaily.getClosePrice();

        BigDecimal lowBigDecimal = new BigDecimal(lowPrice);
        BigDecimal heighBigDecimal = new BigDecimal(heighPrice);
        BigDecimal closeBigDecimal = new BigDecimal(closePrice);

        BigDecimal avgBigDecimal = this.getAvgPrice(heighBigDecimal, lowBigDecimal, closeBigDecimal);

        BigDecimal nextHeighPrice = this.getNextHeighPrice(avgBigDecimal, heighBigDecimal, lowBigDecimal);
        BigDecimal nextSecondHeighPrice = this.getNextSecondHeighPrice(avgBigDecimal, lowBigDecimal);
        BigDecimal nextSecondLowPrice = this.getNextSecondLowPrice(avgBigDecimal, heighBigDecimal);
        BigDecimal nextLowestPrice = this.getNextLowestPrice(avgBigDecimal, heighBigDecimal, lowBigDecimal);
        Prediction prediction = new Prediction();
        prediction.setHighestPrice(nextHeighPrice.toString());
        prediction.setSecondHighPrice(nextSecondHeighPrice.toString());
        prediction.setSecondLowPrice(nextSecondLowPrice.toString());
        prediction.setLowestPrice(nextLowestPrice.toString());
        return prediction;
    }

    /**
     * 计算平均价, 自助预估时才需要计算, 爬取网站数据时,能直接取到
     */
    private BigDecimal getAvgPrice(BigDecimal heighPrice, BigDecimal lowPrice, BigDecimal closePrice) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal heighAndLow = heighPrice.add(lowPrice);
        BigDecimal doubleClose = closePrice.multiply(two);

        BigDecimal four = new BigDecimal("4");
        BigDecimal allPrice = heighAndLow.add(doubleClose);
        return allPrice.divide(four);
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
