package com.github.antlrjavaparser;

import java.util.ArrayList;

public class SharedCount {
	ArrayList<String> sequence;
	int count;
	public ArrayList<String> getSequence() {
		return sequence;
	}
	public void setSequence(ArrayList<String> sequence) {
		this.sequence = sequence;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public SharedCount(ArrayList<String> sequence, int count) {
		 
		this.sequence = sequence;
		this.count = count;
	}
	
}
