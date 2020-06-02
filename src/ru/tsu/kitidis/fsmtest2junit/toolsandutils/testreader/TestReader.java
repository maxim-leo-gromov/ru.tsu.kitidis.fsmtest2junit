package ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader.exceptions.TestReaderBasicException;

public class TestReader {

	public TestReader() {
		// TODO Auto-generated constructor stub
	}
	
public static LinkedList<LinkedList<IOPair>> readTest(String fullPath) throws TestReaderBasicException {
		
		if(fullPath == null)
			throw new TestReaderBasicException("The path, specified to read test from, is null");
		
		if(fullPath.isEmpty())
			throw new TestReaderBasicException("The path, specified to read test from, is empty");
		
		File file = new File(fullPath);
		
		if(!file.exists())
			throw new TestReaderBasicException("The file, specified to read test from, does not exist");
		
		BufferedReader br = null;
		String st = "";
		LinkedList<LinkedList<IOPair>> result = new LinkedList<LinkedList<IOPair>>();
		int lNum = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			while(( st = br.readLine()) != null) {
				lNum++;
				st = st.trim();
				if(st == null || st.isEmpty()) continue;
				String[] parts = st.split("[\\s]+");
				if(parts == null || parts.length < 1)
					throw new TestReaderBasicException("Empty test");
				LinkedList<IOPair> testCase = new LinkedList<IOPair>();
				int anIndex = 0;
				String inp = "";
				String out = "";
				for(int i = 0; i < parts.length; i++) {
					anIndex = parts[i].indexOf('/');
					if(anIndex <= 0 || anIndex >= parts[i].length()) throw new TestReaderBasicException("Wrong input/output pair in the test file at the line " + lNum);
					IOPair aPair = new IOPair();
					inp = parts[i].substring(0, anIndex);
					if(inp == null || inp.length() == 0)
						throw new TestReaderBasicException("Weird input in the test file at the line " + lNum);
					out = parts[i].substring(anIndex + 1);
					if(out == null || out.length() == 0)
						throw new TestReaderBasicException("Weird output in the test file at the line " + lNum);
					aPair.setInput(Integer.parseInt(inp)); 
					aPair.setOutput(Integer.parseInt(out));
					testCase.add(aPair);					
				}
				result.add(testCase);
			}
		} catch (FileNotFoundException e) {
			throw new TestReaderBasicException("File not Found", e);
		} catch (IOException e) {
			throw new TestReaderBasicException("Some Input-Output exception", e);
		}
		finally {
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
