package com.shinley.activiti.model.response;

import com.shinley.activiti.model.ProcessDefinitionModel;
import lombok.Data;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcessDefinitionListResponse implements Serializable {
    private long total;
    private List<ProcessDefinitionModel> list;

}
