/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Mickael Istria (Red Hat) - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.generator.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.eclipse.swtbot.generator.ui.BotGeneratorEventDispatcher.CodeGenerationListener;
import org.eclipse.swtbot.generator.ui.editor.ClassDocument;
import org.eclipse.swtbot.generator.ui.editor.Method;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.PreferenceConstants;

public class RecorderDialog extends TitleAreaDialog {

	private BotGeneratorEventDispatcher recorder;
	private List<Generator> availableGenerators;
	private Map<CTabItem, SourceViewer> tabViewer;
	private CTabFolder classTabFolder;
	private Button recordPauseButton;
	private ToolItem annotationsToolItem;
	private DropdownMethodSelectionListener methodListener;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public RecorderDialog(Shell parentShell,BotGeneratorEventDispatcher recorder, List<Generator> availableGenerators) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE| SWT.RESIZE | SWT.MAX);
		setBlockOnOpen(false);
		this.recorder = recorder;
		this.availableGenerators = availableGenerators;
		this.tabViewer = new HashMap<CTabItem, SourceViewer>();
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("Recorder");
		setTitle("Bot Test Recorder");
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite generatorSelectionContainer = new Composite(container,SWT.NONE);
		generatorSelectionContainer.setLayout(new GridLayout(2, false));
		Label selectorLabel = new Label(generatorSelectionContainer, SWT.NONE);
		selectorLabel.setText("Target Bot API:");
		selectorLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,false));
		
		ComboViewer generatorSelectionCombo = new ComboViewer(generatorSelectionContainer);
		generatorSelectionCombo.setContentProvider(new ArrayContentProvider());
		generatorSelectionCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object o) {
				return ((Generator) o).getLabel();
			}
		});
		generatorSelectionCombo.setInput(this.availableGenerators);
		generatorSelectionCombo.setSelection(new StructuredSelection(this.recorder.getCurrentGenerator()));
		Image image  = this.recorder.getCurrentGenerator().getImage();
		if(image != null){
			image = new Image(Display.getCurrent(), image.getImageData().scaledTo(90, 90));
			setTitleImage(image);
		} else {
			setTitleImage(getDefaultImage());
		}
		
		generatorSelectionCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Generator newGenerator = (Generator) ((IStructuredSelection) event.getSelection()).getFirstElement();
				recorder.setGenerator(newGenerator);
				((DropdownAnnotationSelectionListener)annotationsToolItem.getData()).addItems(recorder.getCurrentGenerator().createAnnotationRules());
				Image image  = newGenerator.getImage();
				if(image != null){
					image = new Image(Display.getCurrent(), image.getImageData().scaledTo(90,90));
					setTitleImage(image);
				} else {
					setTitleImage(getDefaultImage());
				}
			}
		});

		classTabFolder = new CTabFolder(container, SWT.CLOSE |SWT.BORDER);
		classTabFolder.setUnselectedCloseVisible(false);
		classTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true));
		ToolBar addClassToolbar = new ToolBar(classTabFolder, SWT.HORIZONTAL);
		classTabFolder.setTopRight(addClassToolbar);
		ToolItem addClassItem = new ToolItem(addClassToolbar, SWT.NONE);
		addClassItem.setText("+");
		addClassItem.setToolTipText("Add class");
		addClassItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openClassShell();
			}
		});
		
		
		createTabItem(classTabFolder, "FirstClass");

		classTabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
				ClassDocument doc = (ClassDocument) viewer.getDocument();
				if (doc.getActiveMethod() == null) {
					recorder.setRecording(false);
					recordPauseButton.setText("Start Recording");
				}
				((DropdownAnnotationSelectionListener)annotationsToolItem.getData()).update();
			}
		});

		Composite actionsComposite = new Composite(container, SWT.NONE);
		actionsComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		recordPauseButton = new Button(actionsComposite, SWT.PUSH);
		recordPauseButton.setText("Start Recording");
		final Button copyButton = new Button(actionsComposite, SWT.PUSH);
		copyButton.setToolTipText("Copy");
		copyButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));

		recordPauseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (recorder.isRecording()) {
					recorder.setRecording(false);
					recordPauseButton.setText("Start Recording");
				} else {
					SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
					ClassDocument doc = (ClassDocument) viewer.getDocument();
					if(doc.getActiveMethod() == null){
						openMethodShell(true);
					} else {
						recorder.setRecording(true);
						recordPauseButton.setText("Pause");
					}
				}
			}
		});

		this.recorder.addListener(new CodeGenerationListener() {
			public void handleCodeGenerated(List<String> code, String importCode) {
				SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
				ClassDocument doc = (ClassDocument) viewer.getDocument();
				doc.addImport(importCode);
				for (String c : code) {
					doc.addCode(c);
				}
				viewer.setTopIndex(((ClassDocument) viewer.getDocument()).getLastOffset()-4);
			}
		});
		copyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Clipboard cb = new Clipboard(Display.getCurrent());
				TextTransfer textTransfer = TextTransfer.getInstance();
				SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
				cb.setContents(new Object[] { viewer.getDocument().get() }, new Transfer[] { textTransfer });
				cb.dispose();
			}
		});

		return container;
	}

	private void createTabItem(final CTabFolder tabFolder, String text) {
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText(text);

		Composite composite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ToolBar toolBar = new ToolBar(composite, SWT.HORIZONTAL);
		RowLayout rLayout = new RowLayout();
		rLayout.fill = true;
		rLayout.justify = true;
		toolBar.setLayout(rLayout);
		ToolItem tItem = new ToolItem(toolBar, SWT.PUSH);
		tItem.setText("Add method");
		ToolItem tItemDrop = new ToolItem(toolBar, SWT.DROP_DOWN);
		tItemDrop.setText("No active method");

		annotationsToolItem = new ToolItem(toolBar, SWT.DROP_DOWN);
		annotationsToolItem.setText("Method annotation");
		
		final DropdownAnnotationSelectionListener listenerAnnot = new DropdownAnnotationSelectionListener(annotationsToolItem, false);
		annotationsToolItem.addSelectionListener(listenerAnnot);
		annotationsToolItem.setData(listenerAnnot);
		
		ToolItem annotationsClassToolItem = new ToolItem(toolBar, SWT.DROP_DOWN);
		annotationsClassToolItem.setText("Class annotation");
		
		final DropdownAnnotationSelectionListener listenerClassAnnot = new DropdownAnnotationSelectionListener(annotationsToolItem, true);
		annotationsClassToolItem.addSelectionListener(listenerClassAnnot);
		annotationsClassToolItem.setData(listenerClassAnnot);

		methodListener = new DropdownMethodSelectionListener(tItemDrop);
		tItemDrop.addSelectionListener(methodListener);

		tItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openMethodShell(false);
			}
		});

		// create editor with syntax coloring
		final ClassDocument doc = new ClassDocument(text);
		final SourceViewer generatedCode = new SourceViewer(composite, new VerticalRuler(0), SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		JavaSourceViewerConfiguration javaConf = new JavaSourceViewerConfiguration(JavaUI.getColorManager(), PreferenceConstants.getPreferenceStore(), null, null);
		generatedCode.configure(javaConf);
		JavaTextTools tools = new JavaTextTools(PreferenceConstants.getPreferenceStore());
		tools.setupJavaDocumentPartitioner(doc);
		generatedCode.setDocument(doc);
		generatedCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		generatedCode.setEditable(false);
		tabItem.setControl(composite);
		tabFolder.setSelection(tabItem);
		tabViewer.put(tabItem, generatedCode);
		listenerAnnot.update();
		doc.setViewer(generatedCode);
	}

	@Override
	public void createButtonsForButtonBar(Composite parent) {
		// Override to remove default buttons
	}
	
	private void openClassShell(){
		final Shell addClassShell = new Shell(Display.getCurrent());
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		addClassShell.setLayout(gridLayout);
		addClassShell.setSize(375, 125);
		addClassShell.setText("Add new class");
		
		Point pt = Display.getCurrent().getCursorLocation();
		addClassShell.setLocation(pt.x, pt.y);
		
		GridData gridData = new GridData(GridData.FILL,GridData.CENTER, true, false);
		gridData.horizontalSpan = 1;

		final Label classLabel = new Label(addClassShell, SWT.NONE);
		classLabel.setText("Class name: ");
		final Text classText = new Text(addClassShell, SWT.SINGLE | SWT.BORDER);
		classText.setLayoutData(gridData);
		
		final Label image = new Label(addClassShell, SWT.NONE);
	    image.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
	    image.setVisible(false);
		
		final Label errorLabel = new Label(addClassShell, SWT.NONE);
		gridData = new GridData(GridData.FILL,GridData.CENTER, true, false);
		gridData.horizontalSpan = 2;
		errorLabel.setLayoutData(gridData);
		errorLabel.setVisible(false);

		

		gridData = new GridData(GridData.END, GridData.CENTER, false,false);
		gridData.horizontalSpan = 3;
		
		final Button addClassButton = new Button(addClassShell, SWT.PUSH);
		addClassButton.setText("Add");
		addClassButton.setEnabled(false);
		addClassButton.setLayoutData(gridData);
		
		addClassButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createTabItem(classTabFolder, classText.getText());
				classText.setText("");
				recorder.setRecording(false);
				recordPauseButton.setText("Start Recording");
				addClassShell.close();

			}
		});

		classText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				if (!classText.getText().isEmpty() && !classText.getText().contains(" ")) {
					addClassButton.setEnabled(true);
					errorLabel.setVisible(false);
					image.setVisible(false);
				} else {
					addClassButton.setEnabled(false);
					errorLabel.setText("Method name "+classText.getText()+" is invalid!");
					errorLabel.setVisible(true);
					image.setVisible(true);
				}

			}
		});
		addClassShell.open();
	}
	
	
	private void openMethodShell(final boolean fromStartButton){
		final Shell addMethodShell = new Shell(Display.getCurrent());
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		addMethodShell.setLayout(gridLayout);
		addMethodShell.setSize(375, 125);
		addMethodShell.setText("Add new method");

		Point pt = Display.getCurrent().getCursorLocation();
		addMethodShell.setLocation(pt.x, pt.y);
		
		
		GridData gridData = new GridData(GridData.FILL,GridData.CENTER, true, false);
		gridData.horizontalSpan = 1;

		final Label methodLabel = new Label(addMethodShell, SWT.NONE);
		methodLabel.setText("Method name:");
		final Text methodText = new Text(addMethodShell, SWT.SINGLE | SWT.BORDER);
		methodText.setLayoutData(gridData);
		
		final Label image = new Label(addMethodShell, SWT.NONE);
	    image.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
	    image.setVisible(false);
	    
		final Label errorLabel = new Label(addMethodShell, SWT.NONE);
		GridData gridData1 = new GridData(GridData.FILL,GridData.CENTER, true, false);
		gridData.horizontalSpan = 2;
		errorLabel.setLayoutData(gridData1);
		errorLabel.setVisible(false);

		final Button addMethodButton = new Button(addMethodShell, SWT.PUSH | SWT.RIGHT);
		addMethodButton.setText("Add");
		addMethodButton.setEnabled(false);
		gridData = new GridData(GridData.END, GridData.CENTER, false,false);
		gridData.horizontalSpan = 3;
		addMethodButton.setLayoutData(gridData);
		addMethodShell.open();
		
		methodText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
				ClassDocument doc = (ClassDocument) viewer.getDocument();
				if(doc.getMethods().contains(new Method(methodText.getText()))){
					errorLabel.setText("Method with name "+methodText.getText()+" already exists!");
					errorLabel.setVisible(true);
					image.setVisible(true);
					return;
				}
				errorLabel.setVisible(false);
				image.setVisible(false);
				if (!methodText.getText().isEmpty() && !methodText.getText().contains(" ")) {
					addMethodButton.setEnabled(true);
				} else {
					addMethodButton.setEnabled(false);
					errorLabel.setText("Method name "+methodText.getText()+" is invalid!");
					errorLabel.setVisible(true);
					image.setVisible(true);
				}

			}
		});

		addMethodButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
				ClassDocument doc = (ClassDocument) viewer.getDocument();
				doc.addMethod(methodText.getText());
				methodListener.add(methodText.getText());
				if(fromStartButton){
					recorder.setRecording(true);
					recordPauseButton.setText("Pause");
				}
				addMethodShell.close();
			}
		});

	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(585, 650);
	}

	class DropdownMethodSelectionListener extends SelectionAdapter {
		private ToolItem dropdown;

		private Menu menu;

		public DropdownMethodSelectionListener(ToolItem dropdown) {
			this.dropdown = dropdown;
			menu = new Menu(dropdown.getParent());
		}

		public void add(String item) {
			MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setText(item);
			menuItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent event) {
					MenuItem selected = (MenuItem) event.widget;
					dropdown.setText(selected.getText());
					SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
					ClassDocument doc = (ClassDocument) viewer.getDocument();
					doc.setActiveMethod(selected.getText());
					((DropdownAnnotationSelectionListener)annotationsToolItem.getData()).update();
					viewer.setTopIndex(((ClassDocument) viewer.getDocument()).getLastOffset()-4);
				}
			});
			dropdown.setText(item);
			SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
			ClassDocument doc = (ClassDocument) viewer.getDocument();
			doc.setActiveMethod(menuItem.getText());
			((DropdownAnnotationSelectionListener)annotationsToolItem.getData()).update();

		}
		public void widgetSelected(SelectionEvent event) {
			if (event.detail == SWT.ARROW) {
				ToolItem item = (ToolItem) event.widget;
				Rectangle rect = item.getBounds();
				Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
				menu.setLocation(pt.x, pt.y + rect.height);
				menu.setVisible(true);
			}
		}
	}

	class DropdownAnnotationSelectionListener extends SelectionAdapter {

		private ToolItem dropDown;
		private Menu menu;
		private boolean classAnnotation;

		public DropdownAnnotationSelectionListener(ToolItem dropdown, boolean classAnnotation) {
			this.dropDown=dropdown;
			menu = new Menu(dropdown.getParent());
			List<AnnotationRule> rulesToAdd = new ArrayList<AnnotationRule>();
			this.classAnnotation = classAnnotation;
			if(classAnnotation){
				for(AnnotationRule r: recorder.getCurrentGenerator().createAnnotationRules()){
					if(r.isClassAnnotation()){
						rulesToAdd.add(r);
					}
				}
			} else {
				for(AnnotationRule r: recorder.getCurrentGenerator().createAnnotationRules()){
					if(!r.isClassAnnotation()){
						rulesToAdd.add(r);
					}
				}
			}
			addItems(rulesToAdd);
		}

		public void addItems(List<AnnotationRule> items) {
			menu = new Menu(dropDown.getParent());
			if(items != null){
				for (AnnotationRule item : items) {
					final MenuItem menuItem = new MenuItem(menu, SWT.CHECK);
					menuItem.setText(item.getAnnotation());
					menuItem.setData(item);
					menuItem.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent event) {
							MenuItem selected = (MenuItem) event.widget;
							SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
							ClassDocument doc = (ClassDocument) viewer.getDocument();
							if (selected.getSelection()) {
								if(!classAnnotation){
									doc.addAnnotation((AnnotationRule) menuItem.getData());
								} else {
									doc.addClassAnnotation((AnnotationRule) menuItem.getData());
								}
							} else {
								if(!classAnnotation){
									doc.removeAnnotation((AnnotationRule) menuItem.getData());
								} else {
									doc.removeClassAnnotation((AnnotationRule) menuItem.getData());
								}
							}
							update();
						}
					});
				}
			}
		}

		public void update() {
			SourceViewer viewer = tabViewer.get(classTabFolder.getSelection());
			if(viewer != null){
				ClassDocument doc = (ClassDocument) viewer.getDocument();
				if(!classAnnotation){
				if(doc.getActiveMethod() != null){
					dropDown.setEnabled(true);
					for (MenuItem i : menu.getItems()) {
						if (doc.getActiveMethod().getAnnotations().contains(i.getData())) {
							i.setSelection(true);
						} else {
							i.setSelection(false);
						}
					}
				} else {
					dropDown.setEnabled(false);
				}
				} else {
					for (MenuItem i : menu.getItems()) {
						if (doc.getClassAnnotations().contains(i.getData())) {
							i.setSelection(true);
						} else {
							i.setSelection(false);
						}
					}
				}
			}
		}

		public void widgetSelected(SelectionEvent event) {
			if (event.detail == SWT.ARROW) {
				ToolItem item = (ToolItem) event.widget;
				Rectangle rect = item.getBounds();
				Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
				menu.setLocation(pt.x, pt.y + rect.height);
				menu.setVisible(true);
			}
		}
	}
}