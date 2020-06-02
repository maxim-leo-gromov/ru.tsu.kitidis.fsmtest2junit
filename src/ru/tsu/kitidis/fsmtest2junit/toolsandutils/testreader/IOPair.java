package ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader;

public class IOPair {
	
	int input = 0;
	int output = 0;

	public IOPair() {
	}
	
	public IOPair(int i, int o) {
		input = i;
		output = o;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

}
