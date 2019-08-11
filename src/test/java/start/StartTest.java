package start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.InputStream;

/**
 * 流程的开始和结束
 */
public class StartTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从inputstream)
     */
    @Test
    public void deploymentProcessDefinition_inputstream() {
        // 从当前包下加载资源文件
        // 在resources 下建立和包相同的目录结构，编译后，资源文件就会被放到类的包下面
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("开始活动")
                .addInputStream("start.bpmn", inputStreamBpmn)
                .addInputStream("start.png", inputStreamPng)
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
        String processDefinitionKey = "start";
        ProcessInstance pi = processEngine.getRuntimeService()
                .startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }
}
