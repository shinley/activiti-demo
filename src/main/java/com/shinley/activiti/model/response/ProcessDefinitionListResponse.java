package com.shinley.activiti.model.response;

import lombok.Data;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.Serializable;
import java.util.List;

@Data
public class ProcessDefinitionListResponse implements Serializable {
    private long total;
    private List<ProcessDefinition> list;

}
