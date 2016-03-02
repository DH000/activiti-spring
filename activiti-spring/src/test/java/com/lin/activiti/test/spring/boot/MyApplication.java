package com.lin.activiti.test.spring.boot;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
/**
 * 
 * desc:   ComponentScan: 默认为当前package及子package
 * @author xuelin
 * @date   Jan 20, 2016
 */
@ComponentScan(basePackages = {"com.lin.activiti"})
@EnableAutoConfiguration
@ImportResource(value = { "classpath:spring/*.xml" })
// @SpringBootApplication
public class MyApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, new String[0]);
	}
	
	@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService, final TaskService taskService) {
		
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				System.out.println("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
				runtimeService.startProcessInstanceByKey("oneTaskProcess");
				System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
			}
		};
		
	}
	
}
