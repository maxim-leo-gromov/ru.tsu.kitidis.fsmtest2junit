package ru.tsu.kitidis.fsmtest2junit.ui.dialogs;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TestFilesDialog extends Dialog {
	
	// контейнер наших элементов
	Composite container = null;
	
	// jUnit класс
	Label jUnitClassNameLabel = null;
	Text jUnitClassNameText = null;
	Button jUnitClassNameButton = null;
	String jUnitClassName = null;
	
	// описание входных действий
	Label inputActionDescriptionFileLabel = null;
	Text inputActionDescriptionFileText = null;
	Button inputActionDescriptionFileButton = null;
	String inputActionDescriptionFilePath = null;
	
	// описание выходных действий
	Label outputActionDescriptionFileLabel = null;
	Text outputActionDescriptionFileText = null;
	Button outputActionDescriptionFileButton = null;
	String outputActionDescriptionFilePath = null;
	
	// файл с кодом инициализации
	Label iniCodeFileLabel = null;
	Text iniCodeFileText = null;
	Button iniCodeFileButton = null;
	String iniCodeFilePath = null;
	
	// файл с тестом
	Label testFileLabel = null;
	Text testFileText = null;
	Button testFileButton = null;
	String testFilePath = null;
	
	// Файловые диалоги
	FileDialog saveFileDialog = null;
	FileDialog openFileDialog = null;

	public TestFilesDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		// контейнер области построения диалога
		Composite mainContainer = (Composite) super.createDialogArea(parent);
		
		// мы будем строить в своём контейнере
		if(container == null) {
			container = new Composite(mainContainer, SWT.NONE);
			
			saveFileDialog = new FileDialog(this.getShell(), SWT.SAVE);
			openFileDialog = new FileDialog(this.getShell(), SWT.OPEN);
			
			// располагать элементы будем в решётке из 3х столбцов.
			container.setLayout(new GridLayout(3, false));
			container.setLayoutData(new GridData(GridData.FILL_BOTH));
			//container.setBackground(new org.eclipse.swt.graphics.Color(null, 0, 0, 0));
			
			// всё для выбора создаваемого jUnit класса
			// подпись
			jUnitClassNameLabel = new Label(container, SWT.LEFT);
			jUnitClassNameLabel.setText("jUnit class: ");
			//
			// текстовое поле
			jUnitClassNameText = new Text(container, SWT.SINGLE);
			jUnitClassNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//
			// кнопка
			jUnitClassNameButton = new Button(container, SWT.PUSH | SWT.RIGHT);
			jUnitClassNameButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			jUnitClassNameButton.setText("Select ");
			jUnitClassNameButton.addSelectionListener(
						new SelectionAdapter() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								String path = saveFileDialog.open();
								if(path != null && !path.isEmpty()) {
									jUnitClassNameText.setText(path);
									jUnitClassName = path;
								}
							}
						}
					);
			jUnitClassNameButton.setEnabled(false);
			jUnitClassNameButton.setVisible(false);
			
			// всё для выбора файла с описанием входных действий
			// подпись
			inputActionDescriptionFileLabel = new Label(container, SWT.LEFT);
			inputActionDescriptionFileLabel.setText("Input actions description: ");
			//
			// текстовое поле
			inputActionDescriptionFileText = new Text(container, SWT.SINGLE);
			inputActionDescriptionFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//
			// кнопка
			inputActionDescriptionFileButton = new Button(container, SWT.PUSH | SWT.RIGHT);
			inputActionDescriptionFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			inputActionDescriptionFileButton.setText("Select ");
			inputActionDescriptionFileButton.addSelectionListener(
					new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String path = openFileDialog.open();
							if(path != null && !path.isEmpty()) {
								inputActionDescriptionFileText.setText(path);
								inputActionDescriptionFilePath = path;
							}
						}
					}
				);
			
			// всё для выбора файла с описанием выходных действий
			// подпись
			outputActionDescriptionFileLabel = new Label(container, SWT.LEFT);
			outputActionDescriptionFileLabel.setText("Output actions description: ");
			//
			// текстовое поле
			outputActionDescriptionFileText = new Text(container, SWT.SINGLE);
			outputActionDescriptionFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//
			// кнопка
			outputActionDescriptionFileButton = new Button(container, SWT.PUSH | SWT.RIGHT);
			outputActionDescriptionFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			outputActionDescriptionFileButton.setText("Select ");
			outputActionDescriptionFileButton.addSelectionListener(
					new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String path = openFileDialog.open();
							if(path != null && !path.isEmpty()) {
								outputActionDescriptionFileText.setText(path);
								outputActionDescriptionFilePath = path;
							}
						}
					}
				);
			
			// всё для выбора файла с описанием инициализации
			// подпись
			iniCodeFileLabel = new Label(container, SWT.LEFT);
			iniCodeFileLabel.setText("File with inicialization code: ");
			//
			// текстовое поле
			iniCodeFileText = new Text(container, SWT.SINGLE);
			iniCodeFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//
			// кнопка
			iniCodeFileButton = new Button(container, SWT.PUSH | SWT.RIGHT);
			iniCodeFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			iniCodeFileButton.setText("Select ");
			iniCodeFileButton.addSelectionListener(
					new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String path = openFileDialog.open();
							if(path != null && !path.isEmpty()) {
								iniCodeFileText.setText(path);
								iniCodeFilePath = path;
							}
						}
					}
				);
			
			// всё для выбора файла с тестом
			// подпись
			testFileLabel = new Label(container, SWT.LEFT);
			testFileLabel.setText("FSM test file: ");
			//
			// текстовое поле
			testFileText = new Text(container, SWT.SINGLE);
			testFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//
			// кнопка
			testFileButton = new Button(container, SWT.PUSH | SWT.RIGHT);
			testFileButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
			testFileButton.setText("Select ");
			testFileButton.addSelectionListener(
					new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String path = openFileDialog.open();
							if(path != null && !path.isEmpty()) {
								testFileText.setText(path);
								testFilePath = path;
							}
						}
					}
				);
		}
		else container.setParent(parent);
		
		return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Building jUnit test from FSM test");
	}
	
	@Override
	public boolean isResizable() {
		return true;
	}

	public String getJUnitClassName() {
		return this.jUnitClassName;
	}
	
	public String getTestFilePath() {
		return this.testFilePath;
	}
	
	public String getInputActionDescriptionFilePath() {
		return this.inputActionDescriptionFilePath;
	}
	
	public String getOutputActionDescriptionFilePath() {
		return this.outputActionDescriptionFilePath;
	}
	
	public String getIniCodeFilePath() {
		return this.iniCodeFilePath;
	}
	
	@Override
	public boolean close() {
		jUnitClassName = this.jUnitClassNameText.getText();
		testFilePath = this.testFileText.getText();
		inputActionDescriptionFilePath = this.inputActionDescriptionFileText.getText();
		outputActionDescriptionFilePath = this.outputActionDescriptionFileText.getText();
		iniCodeFilePath = this.iniCodeFileText.getText();
		return super.close();
	}
}
