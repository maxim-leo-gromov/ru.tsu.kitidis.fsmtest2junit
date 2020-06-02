package ru.tsu.kitidis.fsmtest2junit.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

public class ToolInfoHandler extends AFSMTest2JUunitUIHandler {
	
	/**
	 * The constructor.
	 */
	public ToolInfoHandler() {
		super();
	}

	/**
	 * The actual execute
	 */
	public Object theJob(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		/*MessageDialog.openInformation(
				window.getShell(),
				"FSMTest To JUnit",
				FSMTest2JUnitPluginActivator.getDefault().getTheSelectedItem().getClass().toString());*/
		
		MessageDialog.openInformation(
				window.getShell(),
				"FSMTest To JUnit",
				"FSM test suite to JUnit test plug-in for Eclipse.\nDeveloped by (c) Maxim Gromov\nRead more here: Gromov M.L., Prokopenko S.A., Shabaldina N.V., Laputenko A.V. Model Based JUnit Testing //20th International Conference of Young Specialists on Micro/Nanotechnologies and Electron Devices. EDM 2019 : conference proceedings. [S. l.]: IEEE, 2019. P. 139-142. URL: ISBN 978-1-7281-1753-9");
		
		return null;
	}
}
