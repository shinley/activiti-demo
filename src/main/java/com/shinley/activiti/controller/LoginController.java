package com.shinley.activiti.controller;

import com.shinley.activiti.business.EmpUserBiz;
import com.shinley.activiti.model.EmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private EmpUserBiz empUserBiz;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody EmpUser empUser, HttpServletRequest request) {
        boolean result = empUserBiz.checkEmpUser(empUser.getUserName(), empUser.getPassword());
        if (result) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", empUser.getUserName());
            return true;
        }
        return false;
    }
}
