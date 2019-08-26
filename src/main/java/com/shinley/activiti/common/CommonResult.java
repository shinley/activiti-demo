package com.shinley.activiti.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult implements Serializable {


    public CommonResult(Object data) {
        this.data = data;
    }

    private int code = 200;
    private String message = "success";
    private Object data;
}
