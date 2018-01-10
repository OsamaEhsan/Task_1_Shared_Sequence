package com.github.antlrjavaparser;

public class SharedCount {
	String sequence;
	int count;
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public SharedCount(String sequence, int count) {
		 
		this.sequence = sequence;
		this.count = count;
	}
	
}
