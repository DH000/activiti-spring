package com.lin.activiti.task.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class ExampelTaskListener implements TaskListener {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 1836123579918220078L;

	@Override
	public void notify(DelegateTask delegateTask) {
		switch (delegateTask.getEventName()) {
			case EVENTNAME_CREATE:
				System.out.println("task listener was created.");
				break;
			case EVENTNAME_DELETE:
				System.out.println("task listener was deleted.");
				break;
			case EVENTNAME_COMPLETE:
				System.out.println("task listener is completed.");
				break;
			case EVENTNAME_ASSIGNMENT:
				System.out.println("task listener was assignmented.");
				break;
				
			default:
				break;
		}
		
	}
	
}
