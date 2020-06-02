package ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader;

/*
 *  TypeSignature ::=
     "B"  // byte
   | "C"  // char
   | "D"  // double
   | "F"  // float
   | "I"  // int
   | "J"  // long
   | "S"  // short
   | "V"  // void
   | "Z"  // boolean
   | "T" + Identifier + ";" // type variable
   | "[" + TypeSignature  // array X[]
   | "!" + TypeSignature  // capture-of ?
   | "|" + TypeSignature + (":" + TypeSignature)+ // intersection type
   | ResolvedClassTypeSignature
   | UnresolvedClassTypeSignature

 */

public class InputStimulus {
	// индексы типов. Должны идти от 0 через 1
	public final static int VOID =		0;
	public final static int BYTE =		1;
	public final static int CHAR =		2;
	public final static int DOUBLE =	3;
	public final static int FLOAT =		4;
	public final static int INT =		5;
	public final static int LONG =		6;
	public final static int SHORT =		7;
	public final static int BOOLEAN =	8;
	public final static int STRING =	9;
	public final static int OBJECT =	10;
	
	// имена (зарезервированные слова) типов. Должны соответсвовать индексам
	public final static String[] TYPE_NAMES = {
		"void",
		"byte",
		"char",
		"double",
		"float",
		"int",
		"long",
		"short",
		"boolean",
		"String",
		"Object",
	};
		
	private int returnType = VOID;
	private String callBody = "";
	
	public InputStimulus() {
		
	}
	
	
	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public String getCallBody() {
		return callBody;
	}

	public void setCallBody(String callBody) {
		this.callBody = callBody;
	}
}
