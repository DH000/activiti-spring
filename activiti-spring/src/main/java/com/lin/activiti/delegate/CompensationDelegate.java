package com.lin.activiti.delegate;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class CompensationDelegate implements ActivityBehavior {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 4349674208853331535L;

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		Boolean isCompensate = (Boolean) execution.getVariable("isCompensate");
		if(null != isCompensate && isCompensate){
			System.out.println("抛出补偿事件");
			throw new BpmnError("throwCompensationEvent");
		}else{
			System.out.println("不做补偿处理");
		}
	}
	
}
