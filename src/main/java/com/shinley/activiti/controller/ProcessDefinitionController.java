package com.shinley.activiti.controller;

import com.shinley.activiti.business.ProcessDefinitionBiz;
import com.shinley.activiti.model.param.PaginationParam;
import com.shinley.activiti.model.response.ProcessDefinitionListResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
public class ProcessDefinitionController {

    @Autowired
    private ProcessDefinitionBiz processDefinitionBiz;

    @GetMapping("/prodefinition/list")
    public ProcessDefinitionListResponse prodefinitionList(PaginationParam paginationParam) {
        return processDefinitionBiz.prodefinitionList(paginationParam.getPageIndex(), paginationParam.getPageSize(),
                paginationParam.getKeyword());
    }

    @GetMapping("/prodefinition/viewpic")
    public void viewPic(String deploymentId, String imageName, HttpServletResponse response) {
        InputStream inputStream = processDefinitionBiz.viewProcessImage(deploymentId, imageName);
        try {
            OutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("获取图片资源异常:{}", e.getMessage());
        }
    }

    @DeleteMapping("/prodefinition/delete")
    public boolean deleteProcessDefinition(String deploymentId) {
        processDefinitionBiz.deleteProcessDefinition(deploymentId);
        return true;
    }
}
