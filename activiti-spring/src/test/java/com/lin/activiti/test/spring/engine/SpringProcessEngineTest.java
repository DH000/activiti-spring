package com.lin.activiti.test.spring.engine;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class SpringProcessEngineTest {
	
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	@Test
	public void init(){
		
	}
	
	@Test
	public void useQueryAPITest(){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("Kermit").processVariableValueEquals("employeeName", "Kermit").orderByTaskId().desc().list();
		System.out.println("task数量：" + tasks.size());
	}
	
	@Test
	public void publishHelloProcessTest(){
		repositoryService.createDeployment().addClasspathResource("bpmn/hello.bpmn20.xml").deploy();
		List<Task> tasks = taskService.createTaskQuery().list();
		System.out.println("task数量：" + tasks.size());
	}
	
	@Test
	public void startHelloProcessTest(){
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("helloProcess");
		Assert.assertNotNull(processInstance);
	}
	
	@Test
	public void startProcessTest(){
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");
		System.out.println("ProcessId: " + processInstance.getProcessInstanceId());
	}
	
	@Test
	public void completeAccountancyTaskTest(){
		Task task = taskService.createTaskQuery().processInstanceId("5001").taskDefinitionKey("writeReportTask").singleResult();
		taskService.complete(task.getId());
	}
	
	@Test
	public void completetManagermentTaskTest(){
		Task task = taskService.createTaskQuery().processInstanceId("5001").taskDefinitionKey("verifyReportTask").singleResult();
		taskService.complete(task.getId());
	}
	
	@Test
	public void checkProcessEndingTimeTest(){
		HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery().processInstanceId("35001").singleResult();
		System.out.println("Process instance end time: " + instance.getEndTime());
	}
	
	@Test
	public void startMessageEventProcessTest(){
		ProcessInstance instance = runtimeService.startProcessInstanceByMessage("newInvoiceMessage");
		System.out.println("ProcessId: " + instance.getId());
	}
	
	@Test
	public void reveiceMessageEventTest(){
		Execution execution = runtimeService.createExecutionQuery().parentId("38").singleResult();
		runtimeService.messageEventReceived("paymentMessage", execution.getId());
	}
	
	@Test
	public void sendSignalStartProcessTest(){
		runtimeService.signalEventReceived("The Signal");
	}
	
	@Test
	public void startTimerBoundaryEventTest(){
		runtimeService.startProcessInstanceByKey("timerBoundaryEvent");
	}
}







