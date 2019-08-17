package com.shinley.activiti.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/activiti/login", method = RequestMethod.GET)
    public boolean login() {
        return true;
    }
}
