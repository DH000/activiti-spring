package com.lin.activiti.delegate;

import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class ServerBehaviorDelegate implements ActivityBehavior {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = -3186690974157656760L;

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		System.out.println(">>>>>>>>>>ServerBehaviorDelegate is executed.<<<<<<<<<");
	}
	
}
