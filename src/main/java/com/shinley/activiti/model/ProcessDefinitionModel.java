package com.shinley.activiti.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcessDefinitionModel implements Serializable {
    private String id;
    private String deploymentId;
    private String key;
    private String name;
    private int version;
    private String diagramResourceName;

}
