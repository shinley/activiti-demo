package com.shinley.activiti.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 股票的每次
 */
@Data
public class StockDaily implements Serializable {
    /**
     * 主键
     */
    private int id;
    /**
     * 股票代码
     */
    private String code;
    /**
     * 开盘价
     */
    private String openPrice;
    /**
     * 最高价
     */
    private String heightPrice;
    /**
     * 最低价
     */
    private String lowPrice;
    /**
     * 收盘价
     */
    private String closePrice;
    /**
     * 平均价
     */
    private String avgPrice;
    /**
     * 涨停价
     */
    private String topLimit;
    /**
     * 跌停价
     */
    private String downLimit;
    /**
     * 日期
     */
    private LocalDate date;
    /**
     * 创建日期
     */
    private LocalDateTime localDateTime;
}
