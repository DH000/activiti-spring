package com.lin.activiti.service;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Transactional
	public void startProcess() {
		runtimeService.startProcessInstanceByKey("oneTaskProcess");
	}
	
	@Transactional
	public List<Task> getTasks(String assignee) {
		return taskService.createTaskQuery().executionId("20001").list();
	}
	
	@Transactional
	public void throwException(){
		throw new RuntimeException();
	}
}
