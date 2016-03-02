package com.lin.activiti.delegate;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class CancelDelegate implements ActivityBehavior {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 4976273854065678592L;

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		System.out.println("cancel activiti transcation.");
		throw new BpmnError("200");
	}
	
}
