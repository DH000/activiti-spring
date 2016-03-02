package com.lin.activiti.execution.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.Expression;

public class MultInstanceExceutionListener implements ExecutionListener {
	
	private Expression name;
	
	public Expression getName() {
		return name;
	}

	public void setName(Expression name) {
		this.name = name;
	}

	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 9093447604511598788L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println(name.getValue(execution) + " exceution " + execution.getId() + " start at " + System.currentTimeMillis());
	}
	
}
