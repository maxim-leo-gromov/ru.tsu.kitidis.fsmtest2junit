package ru.tsu.kitidis.fsmtest2junit.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import ru.tsu.kitidis.fsmtest2junit.FSMTest2JUnitPluginActivator;

public abstract class AFSMTest2JUunitUIHandler extends AbstractHandler {

	public AFSMTest2JUunitUIHandler() {
		super();
	}
	
	public abstract Object theJob(ExecutionEvent event) throws ExecutionException;

	@Override
	final public Object execute(ExecutionEvent event) throws ExecutionException {
		// Активное окно в котором произошло событие
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		// обработчик выделения
		ISelectionService service = window.getSelectionService();
		
		// сами выделенные (выбранные) элементы
		IStructuredSelection structured = (IStructuredSelection) service.getSelection();
		
		// первый элемент из области выделения. Остальные не рассматриваем, поскольку
		// предполагаем, что если нужно выполнить действие на нескольких элементах,
		// то это делается перебором вручную
		Object theItem = structured.getFirstElement();
		
		if(theItem instanceof org.eclipse.jdt.core.IType) {
			// Запоминаем eclipse-проект, в котором выбран элемент для действия
			FSMTest2JUnitPluginActivator.getDefault().setTheProject(
					((org.eclipse.jdt.core.IType) theItem).getJavaProject().getProject()
			);
			
			// Запоминаем выбранный элемент
			FSMTest2JUnitPluginActivator.getDefault().setTheSelectedItem((org.eclipse.jdt.core.IType) theItem);
		}
		
		return theJob(event);
	}

}
