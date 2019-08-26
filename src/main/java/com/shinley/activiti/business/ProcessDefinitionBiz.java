package com.shinley.activiti.business;

import com.shinley.activiti.model.ProcessDefinitionModel;
import com.shinley.activiti.model.response.ProcessDefinitionListResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcessDefinitionBiz {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    public ProcessDefinitionListResponse prodefinitionList(int pageIndex, int pageSize, String keyword) {
        ProcessDefinitionListResponse processDefinitionListResponse = new ProcessDefinitionListResponse();
        long count = repositoryService.createProcessDefinitionQuery()
                .processDefinitionNameLike("%"+keyword+"%")
                .latestVersion()
                .count();

        int start = (pageIndex - 1) * pageSize;
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionNameLike("%"+keyword+"%")
                .latestVersion()
                .listPage(start, pageSize);

        List<ProcessDefinitionModel> processDefinitionModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ProcessDefinition processDefinition : list) {
                ProcessDefinitionModel processDefinitionModel = new ProcessDefinitionModel();
                processDefinitionModel.setId(processDefinition.getId());
                processDefinitionModel.setName(processDefinition.getName());
                processDefinitionModel.setKey(processDefinition.getKey());
                processDefinitionModel.setVersion(processDefinition.getVersion());
                processDefinitionModel.setDeploymentId(processDefinition.getDeploymentId());
                processDefinitionModel.setDiagramResourceName(processDefinition.getDiagramResourceName());
                processDefinitionModels.add(processDefinitionModel);
            }
        }
        processDefinitionListResponse.setList(processDefinitionModels);
        processDefinitionListResponse.setTotal(count);
        return processDefinitionListResponse;
    }

    public void deleteProcessDefinition(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }


    public InputStream viewProcessImage(String deploymentId, String imageName) {
        InputStream resourceAsStream = repositoryService.getResourceAsStream(deploymentId, imageName);
        return resourceAsStream;
    }
}
