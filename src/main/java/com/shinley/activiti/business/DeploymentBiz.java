package com.shinley.activiti.business;

import com.shinley.activiti.common.CommonException;
import com.shinley.activiti.model.response.DeploymentListResponse;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@Slf4j
@Component
public class DeploymentBiz {

    @Autowired
    private RepositoryService repositoryService;

    public DeploymentListResponse deploymentList(int pageIndex, int pageSize, String keyword) {

        DeploymentListResponse deploymentListResponse = new DeploymentListResponse();
        long total = repositoryService.createDeploymentQuery().deploymentNameLike("%"+keyword+"%").count();
        int start = (pageIndex - 1) * pageSize;
        List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%"+keyword+"%").listPage(start, pageSize);
        deploymentListResponse.setList(list);
        deploymentListResponse.setTotal(total);
        return deploymentListResponse;
    }


    public void deploy(String deploymentName, MultipartFile file) {
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
    }

    public void deleteDeployment(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }
}
