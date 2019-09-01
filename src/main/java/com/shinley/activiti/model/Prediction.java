package com.shinley.activiti.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 预测的股票价格
 *
 * @author shinley
 */
@Data
public class Prediction implements Serializable {
    /**
     * 主键
     */
    private int id;
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名称
     */
    private String name;
    /**
     * 最高价
     */
    private String highestPrice;
    /**
     * 次高价
     */
    private String secondHighPrice;
    /**
     * 次低价
     */
    private String secondLowPrice;
    /**
     * 最低价
     */
    private String lowestPrice;
    /**
     * 预测日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate stockDate;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
