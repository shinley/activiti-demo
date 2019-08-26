package com.shinley.activiti.model.response;

import com.shinley.activiti.model.DeploymentModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeploymentListResponse implements Serializable {
    private long total;
    private List<DeploymentModel> list;

}
