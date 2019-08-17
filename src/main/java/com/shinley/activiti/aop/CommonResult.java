package com.shinley.activiti.aop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommonResult<T> implements Serializable {


    public CommonResult(T data) {
        this.data = data;
    }

    private int code = 200;
    private String message;
    private T data;
}
