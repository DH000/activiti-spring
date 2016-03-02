package com.lin.activiti.execution.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class EventExecutionListener implements ExecutionListener {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 4609374035113365185L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		switch (execution.getEventName()) {
			case EVENTNAME_START:
				System.out.println("start execution listener.");
				break;
			case EVENTNAME_TAKE:
				System.out.println("take execution listener.");
				break;
			case EVENTNAME_END:
				System.out.println("end execution listener.");
				break;
			default:
				System.out.println("execution listener event name: " + execution.getEventName());
				break;
		}
	}
	
}
