package ru.tsu.kitidis.fsmtest2junit.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import java.io.ByteArrayInputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;

import ru.tsu.kitidis.fsmtest2junit.FSMTest2JUnitPluginActivator;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.ErrorStatusBuilder;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inicodereader.IniCodeReader;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inicodereader.exceptions.IniCodeReaderBasicException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.InputStimuliReader;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.InputStimulus;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.inputstimulireader.exceptions.InputStimuliReaderBasicException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.outputreactionsreader.OutputReactionsReader;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.outputreactionsreader.exceptions.OutputReactionsReaderBasicException;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader.IOPair;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader.TestReader;
import ru.tsu.kitidis.fsmtest2junit.toolsandutils.testreader.exceptions.TestReaderBasicException;
import ru.tsu.kitidis.fsmtest2junit.ui.dialogs.TestFilesDialog;

public class BuildJUnitTestCaseHandler extends AFSMTest2JUunitUIHandler {

	public BuildJUnitTestCaseHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object theJob(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		/*
		if(FSMTest2JUnitPluginActivator.getDefault().getTheSelectedItem() instanceof org.eclipse.jdt.core.IType) {
			//org.eclipse.jdt.core.IType item = (org.eclipse.jdt.core.IType) FSMTest2JUnitPluginActivator.getDefault().getTheSelectedItem();
			IMethod[] methods = null;
			
			try {
				methods = FSMTest2JUnitPluginActivator.getDefault().getTheSelectedItem().getMethods();
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; (methods != null) && (i < methods.length); i++) {
				try {
					System.out.println(methods[i].getReturnType() + " " + methods[i].getElementName());
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		*/
		
		IType item =  FSMTest2JUnitPluginActivator.getDefault().getTheSelectedItem();
		IProject project = FSMTest2JUnitPluginActivator.getDefault().getTheProject();
		
		if(item == null) {
			MultiStatus status = ErrorStatusBuilder.createMultiStatus("The item to work with is null", new Exception("The item to work with is null"));
			org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
					window.getShell(),
					"Error with item",
					"The item to work with is null",
					status,
					IStatus.ERROR);
			errDlg.open();
			return null;
		}
		
		if(project == null) {
			MultiStatus status = ErrorStatusBuilder.createMultiStatus("The project to work with is null", new Exception("The project to work with is null"));
			org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
					window.getShell(),
					"Error with project",
					"The project to work with is null",
					status,
					IStatus.ERROR);
			errDlg.open();
			return null;
		}
		
		InputStimulus[] inputs = null;
		String[] outputs = null;
		LinkedList<LinkedList<IOPair>> test = null;
		String junitClassName = null;
		String[] initializationPart = null;
		String CUTName = item.getElementName();
		
		TestFilesDialog tfd = new TestFilesDialog(window.getShell());
		if(tfd.open() == TestFilesDialog.OK) {
			// получаем входные воздействия
			try {
				inputs = InputStimuliReader.readStimuli(tfd.getInputActionDescriptionFilePath());
			}
			catch (InputStimuliReaderBasicException e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Error with inputs' description file",
						"An error has been encountered with inputs' description file:" + e.getMessage(),
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			
			// получаем выходные реакции
			try {
				outputs = OutputReactionsReader.readReactions(tfd.getOutputActionDescriptionFilePath());
			}
			catch (OutputReactionsReaderBasicException e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Error with outputs' description file",
						"An error has been encountered with outputs' description file:" + e.getMessage(),
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			
			// получаем тест
			try {
				test = TestReader.readTest(tfd.getTestFilePath());
			}
			catch (TestReaderBasicException e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Error with the test file",
						"An error has been encountered with the test file:" + e.getMessage(),
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			
			// получаем имя JUnit класса
			junitClassName = tfd.getJUnitClassName();
			if(junitClassName == null || junitClassName.isEmpty()) {
				junitClassName = CUTName + "_JUTestClass"; 
			}
			
			// получаем код инициализации (доп. поля и сам код)
			try {
				initializationPart = IniCodeReader.readIniCode(tfd.getIniCodeFilePath());
			}
			catch (IniCodeReaderBasicException e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Error with the ini code file",
						"An error has been encountered with the inicode file: " + e.getMessage(),
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			
			// строим JUnit класс
			// 10 создать файл для junit класса в проекте
			IPath path = null;
			IFile file = null;
			OutputStreamWriter juOutStream = null;
		    
			path = item.getResource().getProjectRelativePath().removeLastSegments(1).addTrailingSeparator().append(junitClassName + ".java");
			if(project.exists(path)) {
				String[] buttonsNames = {"Yes", "No"};
				MessageDialog dlg = new MessageDialog(
							window.getShell(),
							"File exists",
							null,
							"The file " + junitClassName + ".java" + " exists in the project. Rewrite?",
							MessageDialog.QUESTION,
							buttonsNames,
							0
						);
				if(dlg.open() != 0) return null; 
			}
			file = project.getFile(path);
			try {
				if(file.exists()) file.delete(true, null);
				file.create(new ByteArrayInputStream("".getBytes()), true, null);
			} catch (CoreException e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Cannot create file",
						"Cannot create file " + junitClassName + ".java",
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			
			
			try {
				System.out.println(file.getLocation().toString());
				juOutStream = new OutputStreamWriter(new java.io.FileOutputStream(new java.io.File(file.getLocation().toString())));
				// 20 записать в файл заголовок (import)
				//file.appendContents(new ByteArrayInputStream(("package " + item.getPackageFragment().getElementName() + ";").getBytes()), true, false, null);
				String eol = System.getProperty("line.separator"); 
				juOutStream.write("package " + item.getPackageFragment().getElementName() + ";" + eol);
				juOutStream.write(eol);
				juOutStream.write("import static org.junit.Assert.*;" + eol);
				juOutStream.write("import org.junit.After;" + eol);
				juOutStream.write("import org.junit.Before;" + eol);
				juOutStream.write("import org.junit.Test;" + eol);
				if(initializationPart != null && initializationPart.length > 0 && initializationPart[0] != null && !initializationPart[0].trim().isEmpty()) {
					juOutStream.write("/* Imports, provided by the User */" + eol);
					juOutStream.write(initializationPart[0] + eol);
					juOutStream.write(eol);
				}
				
				// 30 записать начало класса и стандартные поля
				juOutStream.write("public class " + junitClassName + " {" + eol);
				juOutStream.write("\t" + CUTName + " SUT = null;" + eol);
				// поля void нам не нужно
				for(int i = 1; i < InputStimulus.TYPE_NAMES.length; i++)
					juOutStream.write("\t" + InputStimulus.TYPE_NAMES[i] + " " + InputStimulus.TYPE_NAMES[i] + "Result;" + eol);
				
				// 40 записать доп поля и методы, если есть
				if(initializationPart != null && initializationPart.length > 1 && initializationPart[1] != null && !initializationPart[1].trim().isEmpty()) {
					juOutStream.write(eol);
					juOutStream.write("/* Extra definitions provided by the user */" + eol);
					juOutStream.write(initializationPart[1] + eol);
				}
				// 50 записать setUp метод
				juOutStream.write(eol + eol);
				juOutStream.write("\t@Before" + eol);
				juOutStream.write("\tpublic void setUp() throws Exception {" + eol);
				if(initializationPart != null && initializationPart.length > 2 && initializationPart[2] != null && !initializationPart[2].trim().isEmpty()) {
					juOutStream.write("\t\t/* Initicialization is provided by the user. */" + eol);
					juOutStream.write("\t\t//TODO Make sure, that SUT is defined and callable." + eol);
					juOutStream.write("\t\t" + initializationPart[2] + eol);
				}
				else {
					juOutStream.write("\t\tSUT = new " + CUTName +"();" + eol);
				}
				juOutStream.write("\t}" + eol);
				
				// 60 записать метод tearDown
				juOutStream.write(eol);
				juOutStream.write("\t@After" + eol);
				juOutStream.write("\tpublic void tearDown() throws Exception {" + eol);
				juOutStream.write("\t\tSUT = null;" + eol);
				juOutStream.write("\t}" + eol);
				
				// 70 в цикле создаём тест кейсы
				Iterator<LinkedList<IOPair>> tcIterator = test.iterator();
				IOPair iop = null;
				int tcNum = 0;
				while(tcIterator.hasNext()) {
					Iterator<IOPair> iopIterator = tcIterator.next().iterator();
					juOutStream.write(eol);
					juOutStream.write("\t@Test" + eol);
					juOutStream.write("\tpublic void testCaseNumber" + tcNum + "() {" + eol);
					int iopNum = 0;
					// // 80 цикл по парам
					while(iopIterator.hasNext()) {
						juOutStream.write("\t\t/* Input-Output pair number " + iopNum + " */" + eol);
						// обрамляем try-catch'ем
						juOutStream.write("\t\ttry {" + eol); // открыли try
						iop = iopIterator.next();
						
						if(iop.getInput() >= inputs.length || iop.getInput() < 0) {
							juOutStream.close();
							throw new Exception("The test case number " +
												tcNum +
												" contains an input action indexed "
												+ iop.getInput() +
												". An index should be within 0 and "
												+ (inputs.length - 1)
										);
						}
						
						if(iop.getOutput() >= outputs.length || iop.getOutput() < 0) {
							juOutStream.close();
							throw new Exception("The test case number " +
												tcNum +
												" contains an output reaction indexed "
												+ iop.getOutput() +
												". An index should be within 0 and "
												+ (outputs.length - 1)
										);
						}
						
						if(inputs[iop.getInput()].getReturnType() == InputStimulus.VOID) {
							juOutStream.write("\t\t\tSUT." + inputs[iop.getInput()].getCallBody() + ";" + eol);
						}
						else {
							juOutStream.write("\t\t\t" + InputStimulus.TYPE_NAMES[inputs[iop.getInput()].getReturnType()] + "Result = SUT." + inputs[iop.getInput()].getCallBody() + ";" + eol);
						}
						
						if(iop.getOutput() == 1) { // должно быть исключение
							juOutStream.write("\t\t\tassertTrue(\"Test case number " + tcNum + ", io pair number " + iopNum + " an exception is expected but does not occur\", false);" + eol);
						}
						else { // не должно быть исключения
							juOutStream.write("\t\t\tassertTrue(\"Test case number " + tcNum + ", io pair number " + iopNum + "\", ("  + outputs[iop.getOutput()].replaceAll("@r@", InputStimulus.TYPE_NAMES[inputs[iop.getInput()].getReturnType()] + "Result") + "));" + eol);
						}
						
						juOutStream.write("\t\t}" + eol); // закрыли try
						//catch
						juOutStream.write("\t\tcatch(Exception e) {" + eol); // открыли catch
						if(iop.getOutput() != 1) { // не должно быть исключения
							juOutStream.write("\t\t\tassertTrue(\"For test case number " + tcNum + ", io pair number " + iopNum + " an exception has occured, but should not be\" + e.getMessage(), false);" + eol);
							juOutStream.write("\t\t\te.printStackTrace();" + eol);
						}
						juOutStream.write("\t\t}" + eol + eol); // закрыли catch
						iopNum++;
					}
					juOutStream.write("\t}" + eol);
					tcNum++;
				}
				
				// 90 закрыть класс
				juOutStream.write("}" + eol);
			} catch (Exception e) {
				MultiStatus status = ErrorStatusBuilder.createMultiStatus(e.getLocalizedMessage(), e);
				org.eclipse.jface.dialogs.ErrorDialog errDlg = new org.eclipse.jface.dialogs.ErrorDialog(
						window.getShell(),
						"Error writing file",
						"An error has occured whle writing file: " + e.getMessage(),
						status,
						IStatus.ERROR);
				errDlg.open();
				return null;
			}
			finally {
				try {
					if(juOutStream != null) juOutStream.close();
					file.refreshLocal(IResource.DEPTH_INFINITE, null);
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} // в диалоге нажали OK
		return null;
	}
}

/*
 * // Отладка
			System.out.println("Inputs: ");
			for(int i = 0; i < inputs.length; i++) {
				System.out.println(inputs[i].getReturnType() + "::" + inputs[i].getCallBody());
			}
			System.out.println("Outputs: ");
			for(int i = 0; i < inputs.length; i++) {
				System.out.println(outputs[i]);
			}
			System.out.println("Test");
			Iterator<LinkedList<IOPair>> tcIterator = test.iterator();
			IOPair iop = null;
			while(tcIterator.hasNext()) {
				Iterator<IOPair> iopIterator = tcIterator.next().iterator();
				while(iopIterator.hasNext()) {
					iop = iopIterator.next();
					System.out.print("" + iop.getInput() + "::" +iop.getOutput() + "  ");
				}
				System.out.println();
			}
			
			System.out.println("Test class name");
			System.out.println(junitClassName);
			
			if(initializationPart != null) {
				System.out.println("Initialization.");
				System.out.println("\tExtra fields:");
				System.out.println(initializationPart[0]);
				System.out.println("\tIni code:");
				System.out.println(initializationPart[1]);
			}
			else System.out.println("Basic initialization");
 */