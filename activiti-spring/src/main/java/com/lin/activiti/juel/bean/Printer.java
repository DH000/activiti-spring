package com.lin.activiti.juel.bean;

import org.springframework.transaction.annotation.Transactional;

public class Printer {

	public void printMessage(){
		System.out.println(">>>>>>>>>>hello world<<<<<<<<<<<");
	}
	
	public void printMessage(String msg){
		System.out.println(">>>>>>message: " + msg + "<<<<<<");
	}
	
	@Transactional
	public void throwExceprion(){
		throw new RuntimeException();
	}
	
}
