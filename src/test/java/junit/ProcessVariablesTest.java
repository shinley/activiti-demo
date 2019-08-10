package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;

import java.util.Date;

/***
 * 流程变量
 */
public class ProcessVariablesTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /***
     * 设置流程变量
     */
    @Test
    public void setVariables() {
        TaskService taskService = processEngine.getTaskService();
        String taskId = "1504";
        taskService.setVariableLocal(taskId, "请假天数",3 ); // 与任务ID绑定
        taskService.setVariable(taskId, "请假日期", new Date());
        taskService.setVariable(taskId, "请假原因", "回家控亲");

    }

    /***
     * 获取流程变量
     */
    @Test
    public void getVariables() {
        /**
         * 与任务相关(正在执行)
         */
        TaskService taskService = processEngine.getTaskService();
        /**
         * 任务ID
         */
        String taksId = "1504";
        Integer day = (Integer) taskService.getVariable(taksId, "请假天数");
        Date date = (Date) taskService.getVariable(taksId, "请假日期");
        String reason = (String) taskService.getVariable(taksId, "请假原因");
        System.out.println("请假天数："+ day);
        System.out.println("请假日期：" + date);
        System.out.println("请假原因:" + reason);

    }

    @Test
    public void setAndGetVariables() {
        /**
         * 与流程实例相关，执行对象(正在执行)
         */
        RuntimeService runtimeService = processEngine.getRuntimeService();

        /**
         * 与任务相关（正在执行）
         */
        TaskService taskService = processEngine.getTaskService();

        /**
         * 设置流程变量
         */
        // 表示使用执行对象Id， 变量名称，变量的值(一次只能设置一个值)
//        runtimeService.setVariable(executionId, variableName, value);

        // 入参为执行对象ID, 和Map集合设置流程变量
//        runtimeService.setVariables(executionId, variables);

        /**
         * 任务设置流程变量
         */
        // 表示使用执行对象Id， 变量名称，变量的值(一次只能设置一个值)
        // taskService.setVariable(taskId, variableName, value);
        // 入参为执行对象ID, 和Map集合设置流程变量
        // taskService.setVariables(taskId, variables);

        // 启动流程实例的同时， 可以设置流程变量
        // runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)

        /**
         * 获了流程变量
         */
        // 使用执行对象ID和流程变量的名称，获取流程变量的值
        // runtimeService.getVariable(executionId, variableName)
        // 使用执行对象ID, 获取所有的流程变量，返回Map
        // runtimeService.getVariables(executionId)
    }
}
