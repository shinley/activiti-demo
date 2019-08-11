package parallelgateway;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 并行网关
 */
public class ParallelGateWay {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("parallelGateWay.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("parallelGateWay.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("并行网关")
                .addInputStream("parallelGateWay.bpmn", inputStreamBpmn)
                .addInputStream("parallelGateWay.png", inputStreamPng)
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
        String processDefinitionKey = "parallelGateWay";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "买家";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime()
                .list();

        if(!CollectionUtils.isEmpty(list)) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
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
        String taskId = "";
        Map<String, Object> variables = new HashMap<>();
        variables.put("money", 800);
        processEngine.getTaskService().complete(taskId, variables);
        System.out.println("完成任务，任务ID" + taskId);
    }


}
