package group;

import org.activiti.engine.delegate.DelegateTask;

public class TaskListenerImpl implements org.activiti.engine.delegate.TaskListener {
    /**
     * 用来指定任务的办理人
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.addCandidateUser("灭绝师太");
        delegateTask.addCandidateUser("郭靖");
    }
}
