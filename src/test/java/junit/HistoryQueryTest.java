package junit;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class HistoryQueryTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    /**
     * 查询历史流和实例
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

    /**
     * 查询历史活动
     */
    @Test
    public void findHistoryActiviti() {
        String processInstanceId = "2501";
        List<HistoricActivityInstance> list = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().desc()
                .list();

        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricActivityInstance hai : list) {
                System.out.println(hai.getId() + " " + hai.getProcessInstanceId() + " " + hai.getActivityType() + " "
                        + hai.getStartTime() + " " + hai.getEndTime());
            }
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void findHistoryTask() {
        String processInstanceId = "2501";
        List<HistoricTaskInstance> list = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByTaskCreateTime().asc()
                .list();
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricTaskInstance hti : list) {
                System.out.println(hti.getId() + " " + hti.getName() + " " + hti.getProcessInstanceId()
                        + " " + hti.getStartTime() + " " + hti.getEndTime());
            }
        }
    }

    /**
     * 查询流程变量的历史
     */
    @Test
    public void findHistoryProcessVariables() {
        String processInstanceId = "2501";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if (!CollectionUtils.isEmpty(list)) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId() + " " + hvi.getProcessInstanceId() + " " + hvi.getVariableName()
                        + " " + hvi.getVariableTypeName() + " " + hvi.getValue()  );
            }
        }
    }
}
