package com.lin.activiti.event.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class MyEventListener implements ActivitiEventListener {
	
	@Override
	public void onEvent(ActivitiEvent event) {
		switch (event.getType()) {
			case JOB_EXECUTION_SUCCESS:
				System.out.println("任务调度成功");
				break;
			case JOB_EXECUTION_FAILURE:
				System.out.println("任务调度失败");
				break;
			default:
				System.out.println("事件类型：" + event.getType());
		}
	}
	
	@Override
	public boolean isFailOnException() {
		return false;
	}
	
}
