package com.shinley.activiti.controller;

import com.shinley.activiti.business.DeploymentBiz;
import com.shinley.activiti.model.param.DeploymentParam;
import com.shinley.activiti.model.param.PaginationParam;
import com.shinley.activiti.model.response.DeploymentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class DeploymentController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DeploymentBiz deploymentBiz;


    @GetMapping("/deployment/list")
    public DeploymentListResponse deploymentList(PaginationParam paginationParam) {
        return deploymentBiz.deploymentList(paginationParam.getPageIndex(), paginationParam.getPageSize(),
                paginationParam.getKeyword());
    }

    @PostMapping(value = "/deployment/deploy")
    public String deploy(DeploymentParam deploymentParam) {
        MultipartFile file = deploymentParam.getFile();
        String deploymentName = deploymentParam.getDeploymentName();
        deploymentBiz.deploy(deploymentName, file);
        return "sucess";
    }

    @DeleteMapping(value = "/deployment/delete")
    public String delete(@RequestParam("deploymentId") String deploymentId) {
        deploymentBiz.deleteDeployment(deploymentId);
        return "sucess";
    }
}
