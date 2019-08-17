package group;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupTaskTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("group.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("group.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("组任务")
                .addInputStream("group.bpmn", inputStreamBpmn)
                .addInputStream("group.png", inputStreamPng)
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        // 流程定义的KEY
        String processDefinitionKey = "groupTask";
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
        String assignee = "小A";
        List<Task> list =  processEngine.getTaskService()  // 与正在执行的任务管理相关的
                .createTaskQuery()          // 创建任务查询对象
//                .taskAssignee(assignee) // 指定个人任务查询， 指定办理人
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


    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonalTask() {
        /***
         * 根据流程中连线部分设置的condition，根据流程变量的值，来决定走哪条线
         */
        String taskId = "17504";
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("money", 800);
//        processEngine.getTaskService().complete(taskId, variables);
        processEngine.getTaskService().complete(taskId);
        System.out.println("完成任务，任务ID" + taskId);
    }

    /***
     * 查询正在执行的任务的办理人
     */
    @Test
    public void findRunPersonTask() {
        String taksId = "17504";
        List<IdentityLink> identityLinksForTask = processEngine.getTaskService()
                .getIdentityLinksForTask(taksId);

        if (!CollectionUtils.isEmpty(identityLinksForTask)) {
            for (IdentityLink identityLink : identityLinksForTask) {
                System.out.println(identityLink.getTaskId() + "  " + identityLink.getType() + "  " + identityLink.getProcessInstanceId() + "  " + identityLink.getUserId());
            }
        }

    }

    /**
     * 查询历史任务的办理人表
     */
    @Test
    public void findHistoryPersonTask() {
        String processInstanceId = "17501";
        List<HistoricIdentityLink> historicIdentityLinksForProcessInstance = processEngine.getHistoryService()
                .getHistoricIdentityLinksForProcessInstance(processInstanceId);

        if (!CollectionUtils.isEmpty(historicIdentityLinksForProcessInstance)) {
            for (HistoricIdentityLink historicIdentityLink : historicIdentityLinksForProcessInstance) {
                System.out.println(historicIdentityLink.getTaskId() + "  " + historicIdentityLink.getType()
                        + "  " + historicIdentityLink.getProcessInstanceId() + "  " + historicIdentityLink.getUserId());
            }
        }
    }

    /***
     * 拾取任务，将组务分配给个人， 指定任务的办理人字段
     */
    @Test
    public void claim() {
        // 将组任务分配给个人任务
        // 任务Id
        String taskId = "";
        // 分配的个人任务(可以是组任务的成同， 也可以是非组任务的成员)
        String userId = "大F";
        processEngine.getTaskService()
                .claim(taskId, userId);
    }

    /**
     * 将个人任务回退到组任务
     * 前提， 之前一定是个组任务
     */
    @Test
    public void setAssigee() {
        String taskId = "";
        processEngine.getTaskService()
                .setAssignee(taskId, null);
    }

    /***
     * 向组任务中添加成员
     */
    @Test
    public void addGroupUser() {

        String taskId = "";
        String userId = "";
        processEngine.getTaskService()
                .addCandidateUser(taskId, userId);
    }

    /**
     * 从组任务中删除成员
     */
    @Test
    public void deleteGroupUser() {
        String taskId = "";
        String userId = "";
        processEngine.getTaskService()
                .deleteCandidateUser(taskId, userId);
    }


}
