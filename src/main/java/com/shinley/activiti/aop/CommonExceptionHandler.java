package com.shinley.activiti.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonResult handleException(CommonException commonException) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(500);
        commonResult.setMessage(commonException.getMessage());
        return commonResult;
    }
}
