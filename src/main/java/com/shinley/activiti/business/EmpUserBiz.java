package com.shinley.activiti.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shinley.activiti.dao.EmpUserDao;
import com.shinley.activiti.model.EmpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class EmpUserBiz {

    @Autowired
    private EmpUserDao empUserDao;

    public boolean checkEmpUser(String userName, String password) {
        QueryWrapper<EmpUser> queryWrapper = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", userName);
        params.put("password", password);
        queryWrapper.allEq(params);
        EmpUser empUser = empUserDao.selectOne(queryWrapper);
        if (Objects.nonNull(empUser)) {
            return true;
        }
        return false;
    }

    public IPage<EmpUser> pagenation(int pageIndex, int pageSize) {
        QueryWrapper<EmpUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<EmpUser> empUserPage = new Page<>(pageIndex, pageSize);
        IPage<EmpUser> empUserIPage = empUserDao.selectPage(empUserPage, queryWrapper);
        return empUserIPage;
    }
}
