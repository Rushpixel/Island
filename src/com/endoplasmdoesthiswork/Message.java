package com.endoplasmdoesthiswork;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Message {
	
	public String HEADER;
	public String DATA;
	public InetSocketAddress from;
	
	public Message(String VALUE){
		setFromString(VALUE);
	}
	
	public Message(String VALUE, InetSocketAddress from){
		setFromString(VALUE);
		this.from = from;
	}
	
	public String toString(){
		return HEADER + "%" + DATA;
	}
	
	public void setFromString(String s){
		String ss[] = s.split("%");
		if(ss.length == 2){
			HEADER = ss[0];
			DATA = ss[1];
		} else{
			HEADER = s.replaceAll("%", "");
			DATA = "";
		}
	}

}
