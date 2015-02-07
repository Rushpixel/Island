package com.endoplasm;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JOptionPane;

public class ExceptionHandler implements UncaughtExceptionHandler{

	public void uncaughtException(Thread t, Throwable throwable) {
		throwable.printStackTrace();
		String Message = throwable.toString();
		JOptionPane.showMessageDialog(null, Message, "An exception was did", JOptionPane.ERROR_MESSAGE);
		Endogen.end();
	}
	
	public void ErrorMessage(String Message){
		System.err.println(Message);
		JOptionPane.showMessageDialog(null, Message, "An exception was did", JOptionPane.ERROR_MESSAGE);
		Endogen.end();
	}
	
	public void ErrorMessage(String Message, boolean DoClose){
		System.err.println(Message);
		JOptionPane.showMessageDialog(null, Message, "An exception was did", JOptionPane.ERROR_MESSAGE);
		if(DoClose)Endogen.end();
	}

}
