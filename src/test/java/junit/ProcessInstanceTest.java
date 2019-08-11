package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * 流程实例测试类
 */
public class ProcessInstanceTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义(从zip)
     */
    @Test
    public void deploymentProcessDefinition_zip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInputStream = new ZipInputStream(in);
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程定义")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称" + deployment.getName());
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        ProcessInstance pi = processEngine.getRuntimeService() // 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey("helloword");
        System.out.println(pi.getId());
        System.out.println(pi.getProcessDefinitionId()); // 流程定义ID
        System.out.println(pi.getProcessDefinitionKey()); // 流程定义key
        // 使用流程定义的key启动流程实例， key对应helloworld.bpmn文件中id的属性值，
        // 使用key值启动， 默认是按照最新版本的流程定义启动。

        /**
         * 2501
         * helloword:1:4
         * helloword
         */
    }

    /**
     * 查询流程状态(判断流程正在执行， 还是结束)
     *
     */
    @Test
    public void isProcessEnd() {
        String processInstancedId = "2501";
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstancedId)
                .singleResult();
        if (processInstance == null) {
            System.out.println("流程已经结束");
        } else {
            System.out.printf("流程没有结束");
        }
    }

    /***
     * 查刘历史任务
     */
    @Test
    public void findHistoryTask() {
        String taskAssignee = "张三";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(taskAssignee)
                .list();
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricTaskInstance hti:list) {
                System.out.println(hti.getId() + " " + hti.getName() + "" + hti.getProcessInstanceId()
                        + "" + hti.getStartTime() + " " + hti.getEndTime() + " " + hti.getDurationInMillis() );
            }
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void findHistoryProcessInstance() {
        String processInstanceId = "2501";
        HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (historicProcessInstance != null) {
            System.out.println(historicProcessInstance.getId() + " " +historicProcessInstance.getProcessDefinitionId()
                    + " " + historicProcessInstance.getStartTime() + " " +historicProcessInstance.getEndTime()
                    + " " + historicProcessInstance.getDurationInMillis());
        }
    }
}
