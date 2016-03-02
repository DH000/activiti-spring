package com.lin.activiti.test.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ProcessEngineTest {
	
	private ProcessEngine processEngine;
	
	private RepositoryService repositoryService;
	private RuntimeService runtimeService;
	private TaskService taskService;
	private ManagementService managementService;
	private IdentityService identityService;
	private HistoryService historyService;
	private FormService formService;

	@Before
	public void init(){
		processEngine = ProcessEngines.getDefaultProcessEngine();
		repositoryService = processEngine.getRepositoryService();
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
		managementService = processEngine.getManagementService();
		identityService = processEngine.getIdentityService();
		historyService = processEngine.getHistoryService();
		formService = processEngine.getFormService();
	}
	
	@Test
	public void test(){
		System.out.println(this.processEngine);
		System.out.println(this.repositoryService);
		System.out.println(this.runtimeService);
		System.out.println(this.taskService);
		System.out.println(this.managementService);
		System.out.println(this.identityService);
		System.out.println(this.historyService);
		System.out.println(this.formService);
	}
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	@Deployment(resources = {"bpmn/my-process.bpmn20.xml"})
	public void testUserguideCode(){
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
		Assert.assertNotNull(processInstance);
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		Assert.assertEquals("Activiti is awesome!", task.getName());
		System.out.println("process数量: " + activitiRule.getRepositoryService().createProcessDefinitionQuery().count());
	}
	
	@Test
	public void vacationProcessTest(){
		// 发布请假流程
		repositoryService.createDeployment().addClasspathResource("bpmn/VacationRequest.bpmn20.xml").deploy();
		// 启动工作流
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");
		runtimeService.startProcessInstanceByKey("vacationRequest", variables);
		// 获取task
		Task task = taskService.createTaskQuery().singleResult();
		System.out.println("task名称：" + task.getName());
		System.out.println("process数量：" + repositoryService.createProcessDefinitionQuery().count());
	}
	
	/**
	 * 
	 * taskServer.setVariable: 针对所有task有效 (覆盖)
	 * taskServer.setVariableLocal: 针对当前task有效
	 * 
	 * desc: 
	 * 
	 */
	@Test
	public void completeTaskTest(){
		Task task = taskService.createTaskQuery().singleResult();
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "We have a tight deadline!");
		taskService.complete(task.getId(), taskVariables);
	}
	
	/**
	 * 
	 * desc: 一旦被挂起就不能再开启新的
	 */
	@Test
	public void suspendProcessTest(){
		// 挂起流程
		repositoryService.suspendProcessDefinitionByKey("vacationRequest");
		// 激活
//		repositoryService.activateProcessDefinitionByKey("vacationRequest");
		// 开启新的流程
		runtimeService.startProcessInstanceByKey("vacationRequest");
	}
	
	@Test
	public void userTaskTest(){
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		for(Task task : tasks){
			System.out.println("task名称：" + task.getName());
		}
	}
	
	@Test
	public void removeTask(){
		activitiRule.getTaskService().deleteTask("my-process");
	}
	
	@Test
	public void useQueryAPITest(){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("Kermit").processVariableValueEquals("employeeName", "Kermit").orderByTaskId().desc().list();
		System.out.println("task数量：" + tasks.size());
	}
	
	@Test
	public void userNativeSqlTest(){
		List<Task> tasks = taskService.createNativeTaskQuery().sql("SELECT * FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName}")
		.parameter("taskName", "Adjust vacation request").list();
		System.out.println("task数量：" + tasks.size());
	}
	
	@Test
	public void delopymentProcessTest(){
		repositoryService.createDeployment()
				  .addClasspathResource("bpmn/financial-report.bpmn20.xml")
				  .deploy();
	}
	
}
