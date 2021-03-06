package com.lin.activiti.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lin.activiti.service.MyService;

/**
 * 
 * desc:   RestController = Controller + ResponseBody
 * @author xuelin
 * @date   Jan 20, 2016
 */
@RestController
public class MyRestController {
	@Autowired
	private MyService myService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private RepositoryService repositoryService;
	
	private Map<String, Object> jsonArgumentToMap(HttpServletRequest request){
		try(InputStream is = request.getInputStream()) {
			byte[] buf = new byte[1024];
			StringBuilder argsBuf = new StringBuilder();
			while(is.read(buf) > -1){
				argsBuf.append(new String(buf));
			}
			if(argsBuf.length() > 0){
				return JSON.parseObject(argsBuf.toString().trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyMap();
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public void startProcessInstance() {
		myService.startProcess();
	}
	
	@RequestMapping(value = "/start/{processKey}", method = RequestMethod.POST)
	public String startProcess(@PathVariable String processKey, HttpServletRequest request){
		Map<String, Object> vars = jsonArgumentToMap(request);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, vars);
		return instance.getId();
	}
	
	@RequestMapping(value = "/form/{processKey}", method = RequestMethod.POST)
	public String startFormProcess(@PathVariable String processKey, HttpServletRequest request){
		Map<String, String> vars = new HashMap<>();
		vars.put("numberOfDays", "2");
		vars.put("startDate", "2016-02-05");
		vars.put("vacationMotivation", "rest");
		
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).active().latestVersion().singleResult();
		ProcessInstance instance = formService.submitStartFormData(pd.getId(), vars);
		return instance.getId();
	}
	
	@RequestMapping(value = "/task/{instanceId}", method = RequestMethod.POST)
	public Task completeTask(@PathVariable String instanceId){
		Task task = taskService.createTaskQuery().processInstanceId(instanceId).singleResult();
		System.out.println("finish task " + task.getId());
		taskService.complete(task.getId());
		return task;
	}
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
		List<Task> tasks = myService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName()));
		}
		return dtos;
	}
	
	@RequestMapping(value = "/singal/{singal}", method = RequestMethod.POST)
	public void sendSingl(@PathVariable String singal){
		runtimeService.signalEventReceived(singal);
	}
	
	@RequestMapping(value = "/singal/{intanceId}/{activitiId}", method = RequestMethod.POST)
	public void sendSingl(@PathVariable String activitiId, @PathVariable String intanceId){
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(intanceId).activityId(activitiId).singleResult();
		runtimeService.signal(execution.getId());
	}
	
	@RequestMapping("/signal/{name}")
	public void singal(@PathVariable String name){
		runtimeService.signalEventReceived(name);
	}
	
	@RequestMapping(value = "/msg/{id}/{name}")
	public void sendMessage(@PathVariable String id, @PathVariable String name){
		List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(id).list();
		for(Execution e : list){
			if(StringUtils.hasText(e.getActivityId())){
				System.out.println("current activiti: " + e.getActivityId());
				runtimeService.messageEventReceived(name, e.getId());
			}
		}
	}
	
	public static void main(String[] args) {
		String json = "{\"is\":true}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     ".trim();
		System.out.println(JSON.parseObject(json));
	}
	
	static class TaskRepresentation {
		
		private String id;
		private String name;
		
		public TaskRepresentation(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
