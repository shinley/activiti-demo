package com.shinley.activiti.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CommonResult implements Serializable {


    public CommonResult(Object data) {
        this.data = data;
    }

    private int code = 200;
    private String message = "success";
    private Object data;
}
