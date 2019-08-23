package com.shinley.activiti.business;

import com.shinley.activiti.model.response.ProcessDefinitionListResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessDefinitionBiz {
    @Autowired
    private RepositoryService repositoryService;

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
        processDefinitionListResponse.setList(list);
        processDefinitionListResponse.setTotal(count);
        return processDefinitionListResponse;
    }
}
