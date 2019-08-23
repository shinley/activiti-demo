package com.shinley.activiti.model.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationParam implements Serializable {
    private int pageIndex;
    private int pageSize;
    private String keyword;
}
