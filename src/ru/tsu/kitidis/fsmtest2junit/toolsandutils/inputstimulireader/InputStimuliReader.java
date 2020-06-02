package ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderBasicException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderEmptyPathException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderIOException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderInexistingFileException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderNullPathException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderWrongStimulus;

public class InputStimuliReader {

	public InputStimuliReader() {
	}
	
	public static InputStimulus[] readStimuli(String fullPath) throws InputStimuliReaderBasicException {
		
		if(fullPath == null)
			throw new InputStimuliReaderNullPathException("The path, specified to read stimuli descriptions from, is null");
		
		if(fullPath.isEmpty())
			throw new InputStimuliReaderEmptyPathException("The path, specified to read stimuli descriptions from, is empty");
		
		File file = new File(fullPath);
		
		if(!file.exists())
			throw new InputStimuliReaderInexistingFileException("The file, specified to read stimuli descriptions from, does not exist");
		
		BufferedReader br = null;
		String st = "";
		InputStimulus[] result = null;
		LinkedList<InputStimulus> subResult = new LinkedList<InputStimulus>();
		int lNum = 0;
		try {
			br = new BufferedReader(new FileReader(file));
			while(( st = br.readLine()) != null) {
				lNum++;
				st = st.trim();
				if(st == null || st.isEmpty()) continue;
				String[] parts = st.split("[\\s]+", 2);
				if(parts == null || parts.length < 2)
					throw new InputStimuliReaderWrongStimulus("Wrong stimulus description at the line " + lNum);
				InputStimulus inpStimulus = new InputStimulus();
				inpStimulus.setReturnType(-1);
				for(int i = 0; i < InputStimulus.TYPE_NAMES.length; i++) {
					if(parts[0].equals(InputStimulus.TYPE_NAMES[i])) inpStimulus.setReturnType(i); 
				}
				if(inpStimulus.getReturnType() < 0 || inpStimulus.getReturnType() >= InputStimulus.TYPE_NAMES.length)
					inpStimulus.setReturnType(InputStimulus.VOID);
				inpStimulus.setCallBody(parts[1]);
				subResult.add(inpStimulus);
			}
		} catch (FileNotFoundException e) {
			throw new InputStimuliReaderInexistingFileException("File not Found", e);
		} catch (IOException e) {
			throw new InputStimuliReaderIOException("Some Input-Output exception", e);
		}
		finally {
			try {
				if(br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		result = (InputStimulus[]) subResult.toArray(new InputStimulus[0]);

		return result;
	}

}
