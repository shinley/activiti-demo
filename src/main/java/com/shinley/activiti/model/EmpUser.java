package com.shinley.activiti.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmpUser implements Serializable {
    private int id;
    private String userName;
    private String password;
    private String email;
    private String role;
    private String manager_id;
}
