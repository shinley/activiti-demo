package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

public class TestActiviti {

    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 使用代码创建工作流需要的23张表
     */
    @Test
    public void createTable() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        processEngineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&allowMultiQueries=true&autoReconnect=true");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("chen");

        // ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE; // 不能自动创建表， 需要表存在
//        ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP // 先删除表再创建表
//        processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE  // 如果表不存在， 自动创建表
        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 工作流的核心对象， ProcessEnginee对象
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println("processEngine:" + processEngine);
    }

    @Test
    public void createTable2() {
        // 加载classpath下名为activiti.cfg.xml文件， 创建核 心流程擎对象， 系统数据库会自动创建表
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml")
                .buildProcessEngine();
        System.out.println(processEngine);
    }


    /**
     * 部署流程定义
     */
    @Test
    public void deploymentProcessDefine() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = defaultProcessEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("helloworld入门程序")
                .addClasspathResource("diagrams/Helloworld.bpmn") // 从classpath的资源中加载，一次只能加载一个
                .addClasspathResource("diagrams/Helloworld.png")
                .deploy();

        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
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


    /***
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonalTask() {
        String assignee = "张三";
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

    /**
     * 完成我的任务
     */

    @Test
    public void completeMyPersonalTask() {
        String taskId = "2504";
        processEngine.getTaskService()
                .complete(taskId);
        System.out.println("完成任务, 任务ID:" + taskId);
    }
}
