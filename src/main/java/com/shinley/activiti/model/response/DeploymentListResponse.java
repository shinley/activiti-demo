package com.shinley.activiti.model.response;

import lombok.Data;
import org.activiti.engine.repository.Deployment;

import java.io.Serializable;
import java.util.List;

@Data
public class DeploymentListResponse implements Serializable {
    private long total;
    private List<Deployment> list;

}
