package receive;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiveTask {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("接收活动任务")
                .addInputStream("receiveTask.bpmn", inputStreamBpmn)
                .addInputStream("receiveTask.png", inputStreamPng)
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
        String processDefinitionKey = "receiveTask";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());

        /**
         * 查询执行对象ID
         */
        Execution execution = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processInstanceId(pi.getId())
                .activityId("receiveTask1") // 当前活动的id, 对应receiveTask.bpmn文件中的活动节点id的属性值
                .singleResult();


        /**
         * 使用流程变量充置当日销售额， 用来传递业务参数
         */
        processEngine.getRuntimeService()
                .setVariable(execution.getId(), "汇总当日销售额", 21000);

        /**
         * 向后执行一步， 如果流程处于等待状态， 使得流程继续执行
         */
        processEngine.getRuntimeService()
                .signal(execution.getId());

        /**iD
         * 查询执行对象
         */
        Execution exception2 = processEngine.getRuntimeService()
                .createExecutionQuery()
                .processInstanceId(pi.getId())
                .activityId("receivetask2")
                .singleResult();


        /**
         * 从流程变量中获聂汇总当日销售额的值
         */
        Integer value = (Integer) processEngine.getRuntimeService()
                .getVariable(exception2.getId(), "汇总当日销售额");

        System.out.println("给老板发送短信： 使得流程继续执行:" + value);

        /**
         * 向后执行一步， 如果流程处于等待状态， 使得流程继续执行
         */
        processEngine.getRuntimeService()
                .signal(exception2.getId());
    }

}
