package com.shinley.activiti.controller;

import com.shinley.activiti.common.CommonException;
import com.shinley.activiti.model.param.DeploymentParam;
import com.shinley.activiti.model.response.DeploymentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@RestController
public class DeploymentController {

    @Autowired
    private RepositoryService repositoryService;


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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return deploymentListResponse;
    }

    @PostMapping(value = "/activiti/deployment/deploy")
    public String upload(DeploymentParam deploymentParam) {
        MultipartFile file = deploymentParam.getFile();
        String deploymentName = deploymentParam.getDeploymentName();
        if (file == null || file.isEmpty()) {
            throw new CommonException("文件为空");
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("部署流程失败:{}", e.getMessage());
            throw new CommonException("上传文件异常");
        }
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        repositoryService.createDeployment()
                .name(deploymentName)
                .addZipInputStream(zipInputStream)
                .deploy();
        return "sucess";
    }

    @DeleteMapping(value = "/activiti/deployment/delete")
    public String delete(@RequestParam("deploymentId") String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return "sucess";
    }

    @GetMapping("/activiti/prodefinition/list")
    public List<ProcessDefinition> prodefinitionList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        return list;
    }
}
