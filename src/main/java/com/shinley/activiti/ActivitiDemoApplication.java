package com.shinley.activiti;

import org.activiti.engine.TaskService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.shinley.activiti.dao")
@SpringBootApplication
public class ActivitiDemoApplication {

	@Autowired
	private TaskService taskService;



	public static void main(String[] args) {
		SpringApplication.run(ActivitiDemoApplication.class, args);
	}

}
