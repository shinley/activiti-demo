package com.shinley.activiti.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof CommonResult) {
            return o;
        }
        CommonResult result = new CommonResult();
        result.setData(o);
        return result;
    }

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public CommonResult handleException(CommonException commonException) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(500);
        commonResult.setMessage(commonException.getMessage());
        return commonResult;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult handleAllException(Exception exception) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(500);
        commonResult.setMessage(exception.getMessage());
        return commonResult;
    }
}
