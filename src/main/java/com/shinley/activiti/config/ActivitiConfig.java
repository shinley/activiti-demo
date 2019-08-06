package com.shinley.activiti.config;

import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ActivitiConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EventListener eventListener;

    /**
     * 初始化配置，创建流程引擎表
     *
     * @return
     */
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
//        StandaloneProcessEngineConfiguration configuration = new StandaloneProcessEngineConfiguration();
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        // 设置是否自动更新
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        configuration.setAsyncExecutorActivate(false);

        //configuration.setCustomMybatisMappers(Collections.singleton(ProcessMapper.class));
        //configuration.setActiviti5CustomMybatisXMLMappers(Collections.singleton("classpath:mapper/ProcessMapper.xml"));
        // 注册全局监听器
        List<ActivitiEventListener> activitiEventListener=new ArrayList<>();
        activitiEventListener.add(eventListener);
        configuration.setEventListeners(activitiEventListener);
        return configuration;
    }

    /**
     * 初始化配置，创建流程引擎表
     *
     * @return
     */
    @Bean
    public ProcessEngine getProcessEngine() {
        return processEngineConfiguration().buildProcessEngine();
    }

    /**
     * 仓库服务
     *
     * @return
     */
    @Bean
    public RepositoryService getRepositoryService() {
        return getProcessEngine().getRepositoryService();
    }

    /**
     * 运行时服务
     *
     * @return
     */
    @Bean
    public RuntimeService getRuntimeService() {
        return getProcessEngine().getRuntimeService();
    }

    /**
     * 表单服务
     *
     * @return
     */
    @Bean
    public FormService getFormService() {
        return getProcessEngine().getFormService();
    }

    /**
     * 任务服务
     *
     * @return
     */
    @Bean
    public TaskService getTaskService() {
        return getProcessEngine().getTaskService();
    }

    /**
     * 历史服务
     *
     * @return
     */
    @Bean
    public HistoryService getHistoryService() {
        return getProcessEngine().getHistoryService();
    }

    /**
     * 认证服务
     *
     * @return
     */
    @Bean
    public IdentityService getIdentityService() {
        return getProcessEngine().getIdentityService();
    }

    /**
     * 管理服务
     *
     * @return
     */
    @Bean
    public ManagementService getManagementService() {
        return getProcessEngine().getManagementService();
    }
}
