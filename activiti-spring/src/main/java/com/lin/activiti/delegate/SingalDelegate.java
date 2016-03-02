package com.lin.activiti.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SingalDelegate implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("sending singal.");
		execution.getEngineServices().getRuntimeService().signalEventReceived("alert");
	}
	
}
