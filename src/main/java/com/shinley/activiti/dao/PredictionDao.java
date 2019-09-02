package com.shinley.activiti.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shinley.activiti.model.Prediction;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

public interface PredictionDao extends BaseMapper<Prediction> {
    Prediction findPredictionByCodeDate(@Param("code") String code, @Param("stockDate") LocalDate date);
}
