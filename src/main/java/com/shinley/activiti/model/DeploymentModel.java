package com.shinley.activiti.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeploymentModel implements Serializable {
    private String id;
    private String name;
    private Date deployTime;
}
