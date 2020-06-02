package ru.tsu.kitidis.fsmtest2junit.toolsandutils.outputreactionsreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import ru.tsu.kitidis.fsmtest2junit.toolsandutils.outputreactionsreader.exceptions.OutputReactionsReaderBasicException;

public class OutputReactionsReader {

	public OutputReactionsReader() {
		// TODO Auto-generated constructor stub
	}
	
public static String[] readReactions(String fullPath) throws OutputReactionsReaderBasicException {
		
		if(fullPath == null)
			throw new OutputReactionsReaderBasicException("The path, specified to read reactions' descriptions from, is null");
		
		if(fullPath.isEmpty())
			throw new OutputReactionsReaderBasicException("The path, specified to read reactions' descriptions from, is empty");
		
		File file = new File(fullPath);
		
		if(!file.exists())
			throw new OutputReactionsReaderBasicException("The file, specified to read reactions' descriptions from, does not exist");
		
		BufferedReader br = null;
		String st = "";
		String[] result = null;
		LinkedList<String> subResult = new LinkedList<String>();
		int lNum = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			while(( st = br.readLine()) != null) {
				lNum++;
				st = st.trim();
				if(st == null || st.isEmpty()) continue;
				if(lNum == 1 && !st.matches("true")) {
					throw new OutputReactionsReaderBasicException("The first reaction should be `true'.");
				}
				if(lNum == 2 && !st.matches("Exception")) {
					throw new OutputReactionsReaderBasicException("The second reaction should be `Exception'.");
				}
				String output = new String(st);
				subResult.add(output);
			}
		} catch (FileNotFoundException e) {
			throw new OutputReactionsReaderBasicException("File not Found", e);
		} catch (IOException e) {
			throw new OutputReactionsReaderBasicException("Some Input-Output exception", e);
		}
		finally {
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		result = (String[]) subResult.toArray(new String[0]);

		return result;
	}

}
