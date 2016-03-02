package com.lin.activiti.execution.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.Expression;

public class ExampleExceutionListener implements ExecutionListener {
	
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
	private static final long serialVersionUID = 8910736390584105328L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		switch (execution.getEventName()) {
			case EVENTNAME_START:
				System.out.println(name.getValue(execution) + " is started.");
				break;
			case EVENTNAME_END:
				System.out.println(name.getValue(execution) + " is ended.");
				break;
			case EVENTNAME_TAKE:
				System.out.println(name.getValue(execution) + " is taked.");
				break;
				
			default:
				break;
		}
	}
	
}
