package com.shinley.activiti.controller;

import com.shinley.activiti.aop.CommonException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public List<Deployment> deploymentList() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        if (true) {
            throw new CommonException("测试异常");
        }
        return list;
    }

    @GetMapping("/activiti/prodefinition/list")
    public List<ProcessDefinition> prodefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        return list;
    }
}
