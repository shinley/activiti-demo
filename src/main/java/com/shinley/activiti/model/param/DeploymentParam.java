package com.shinley.activiti.model.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class DeploymentParam implements Serializable {
    private String deploymentName;
    private MultipartFile file;
}
