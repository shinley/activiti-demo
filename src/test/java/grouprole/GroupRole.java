package grouprole;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class GroupRole {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * 添加用户角色组
     */
    @Test
    public void createGroupRole() {

        IdentityService identityService = processEngine.getIdentityService();
        // 创建角色
        identityService.saveGroup(new GroupEntity("总经理"));
        identityService.saveGroup(new GroupEntity("部门经理"));

        // 创建用户
        identityService.saveUser(new UserEntity("张三"));
        identityService.saveUser(new UserEntity("李四"));
        identityService.saveUser(new UserEntity("王五"));

        /**
         * 建立角色和用户的关联关系
         */
        identityService.createMembership("张三", "部门经理");
        identityService.createMembership("李四", "部门经理");
        identityService.createMembership("王五", "总经理");
    }


    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("group_role.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("group_role.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("组角色任务")
                .addInputStream("group_role.bpmn", inputStreamBpmn)
                .addInputStream("group_srole.png", inputStreamPng)
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称" + deployment.getName());
    }

    @Test
    public void startProcessInstance() {
        // 流程定义的key
        String processDefinitionKey = "groupRole";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);

        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /***
     * 查询当前人的组任务
     * 组任务, 使用assignee查不到
     */
    @Test
    public void findGroupTask() {
        String assignee = "张三";
        List<Task> list =  processEngine.getTaskService()  // 与正在执行的任务管理相关的
                .createTaskQuery()          // 创建任务查询对象
                .taskCandidateOrAssigned(assignee)
                .list();

        if (list != null&& list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("创建时间:" + task.getCreateTime());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID" + task.getProcessDefinitionId());
            }
        }
    }

}
