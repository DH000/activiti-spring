package com.lin.activiti.delegate;

import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

public class ServerTaskDelegate implements ActivityBehavior {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = -7110158422288945464L;
	
	private FixedValue name;
	private Expression prefix;
	private FixedValue say;
	
	public FixedValue getName() {
		return name;
	}

	public void setName(FixedValue name) {
		this.name = name;
	}

	public Expression getPrefix() {
		return prefix;
	}

	public void setPrefix(Expression prefix) {
		this.prefix = prefix;
	}

	public FixedValue getSay() {
		return say;
	}

	public void setSay(FixedValue say) {
		this.say = say;
	}

	@Override
	public void execute(ActivityExecution execution) throws Exception {
		System.out.println("----->>>" + getSay().getValue(execution) + " " + getPrefix().getValue(execution) + getName().getValue(execution));
	}
	
}
