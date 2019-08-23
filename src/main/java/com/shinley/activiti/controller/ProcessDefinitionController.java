package com.shinley.activiti.controller;

import com.shinley.activiti.business.ProcessDefinitionBiz;
import com.shinley.activiti.model.param.PaginationParam;
import com.shinley.activiti.model.response.ProcessDefinitionListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessDefinitionController {

    @Autowired
    private ProcessDefinitionBiz processDefinitionBiz;

    @GetMapping("/prodefinition/list")
    public ProcessDefinitionListResponse prodefinitionList(PaginationParam paginationParam) {
        return processDefinitionBiz.prodefinitionList(paginationParam.getPageIndex(), paginationParam.getPageSize(),
                paginationParam.getKeyword());
    }

}
