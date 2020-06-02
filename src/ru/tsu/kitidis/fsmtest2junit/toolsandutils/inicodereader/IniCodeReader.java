package ru.tsu.kitidis.fsmtest2junit.toolsandutils.inicodereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inicodereader.exceptions.IniCodeReaderBasicException;

public class IniCodeReader {
	
	public static final int MAX_FILE_SIZE = 1073741824; // 1Mb

	public IniCodeReader() {
		// TODO Auto-generated constructor stub
	}
	
	public static String[] readIniCode(String fullPath) throws IniCodeReaderBasicException {
		
		if(fullPath == null) return null;
		
		if(fullPath.isEmpty()) return null;
		
		File file = new File(fullPath);
		
		if(!file.exists())
			throw new IniCodeReaderBasicException("The file with an initialization code does not exist");
		
		if (file.length() > MAX_FILE_SIZE) {
	        throw new IniCodeReaderBasicException("The file with an initialization code is too big (more than 10 Mb), I won't be able to handle it");
	    }
		
		byte[] buffer = new byte[(int) file.length()];
	    InputStream ios = null;
	    try {
	        ios = new FileInputStream(file);
	        if (ios.read(buffer) == -1) {
	            throw new IniCodeReaderBasicException("EOF reached while trying to read the whole file with an initialization code");
	        }
	    } catch (IOException e) {
	    	throw new IniCodeReaderBasicException("IO exception while working with the ini code file", e);
	    }
	    finally {
	        try {
	            if (ios != null)
	                ios.close();
	        } catch (IOException e) {}
	    }
		
		String st = null;
		String[] result = null;
		try {
			st = new String(buffer);
			result = st.split("#!", 3);
		} catch (Exception e) {
			throw new IniCodeReaderBasicException("An exception has occured during the work with the ini code", e);
		}
		
		return result;
	}

}
