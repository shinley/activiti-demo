package com.shinley.activiti.controller;

import com.shinley.activiti.common.CommonException;
import com.shinley.activiti.model.response.DeploymentListResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class DeploymentController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/activiti/deploy")
    public boolean deploy(MultipartFile file) {
        return true;
    }

    @GetMapping("/activiti/deployment/list")
    public DeploymentListResponse deploymentList(@RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        DeploymentListResponse deploymentListResponse = new DeploymentListResponse();
        long total = repositoryService.createDeploymentQuery().deploymentNameLike("%"+keyword+"%").count();
        int start = (pageIndex - 1) * pageSize;
        List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%"+keyword+"%").listPage(start, pageSize);
        deploymentListResponse.setList(list);
        deploymentListResponse.setTotal(total);
        return deploymentListResponse;
    }

    @GetMapping("/activiti/prodefinition/list")
    public List<ProcessDefinition> prodefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        return list;
    }
}
