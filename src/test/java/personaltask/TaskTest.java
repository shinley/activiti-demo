package personaltask;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 个人任务的分配
 */
public class TaskTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("任务")
                .addInputStream("task.bpmn", inputStreamBpmn)
                .addInputStream("task.png", inputStreamPng)
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称" + deployment.getName());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcessInstance() {
        // 流程定义的KEY
        String processDefinitionKey = "task";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /***
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "张三丰";
        List<Task> list =  processEngine.getTaskService()  // 与正在执行的任务管理相关的
                .createTaskQuery()          // 创建任务查询对象
                .taskAssignee(assignee) // 指定个人任务查询， 指定办理人
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
