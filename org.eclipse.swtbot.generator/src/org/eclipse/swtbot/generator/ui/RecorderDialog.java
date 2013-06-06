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
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.Generator;
import org.eclipse.swtbot.generator.ui.BotGeneratorEventDispatcher.CodeGenerationListener;
import org.eclipse.swtbot.generator.ui.editor.CodeDocument;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jdt.ui.text.JavaTextTools;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.PreferenceConstants;

public class RecorderDialog extends TitleAreaDialog {

	private BotGeneratorEventDispatcher recorder;
	private List<Generator> availableGenerators;
	private Map<TabItem,SourceViewer> tabViewer = new HashMap<TabItem, SourceViewer>();
	private TabFolder tabFolder;
	private Button recordPauseButton;
	
	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public RecorderDialog(Shell parentShell, BotGeneratorEventDispatcher recorder, List<Generator> availableGenerators) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE | SWT.RESIZE | SWT.MAX);
		setBlockOnOpen(false);
		this.recorder = recorder;
		this.availableGenerators = availableGenerators;
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("Recorder");
		setTitle("SWTBot Test Recorder");
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite generatorSelectionContainer = new Composite(container, SWT.NONE);
		generatorSelectionContainer.setLayout(new GridLayout(2, false));
		Label selectorLabel = new Label(generatorSelectionContainer, SWT.NONE);
		selectorLabel.setText("Target Bot API:");
		selectorLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		ComboViewer comboViewer = new ComboViewer(generatorSelectionContainer);
		comboViewer.setContentProvider(new ArrayContentProvider());
		comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object o) {
				return ((Generator)o).getLabel();
			}
		});
		comboViewer.setInput(this.availableGenerators);
		comboViewer.setSelection(new StructuredSelection(this.recorder.getCurrentGenerator()));
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Generator newGenerator = (Generator) ((IStructuredSelection)event.getSelection()).getFirstElement();
				recorder.setGenerator(newGenerator);
			}
		});
		
		Composite newClass = new Composite(container, SWT.NONE);
		newClass.setLayout(new GridLayout(3, false));
		
		final Label classLabel = new Label(newClass,SWT.NONE);
		classLabel.setText("Class name: ");
		final Text className = new Text(newClass,SWT.SINGLE);
		
		final Button addItem = new Button(newClass, SWT.PUSH);
		addItem.setText("Add");
		addItem.setEnabled(false);

		
		tabFolder = new TabFolder(container, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createTabItem(tabFolder,"FirstClass");
		
		tabFolder.addSelectionListener(new SelectionAdapter() {
			  public void widgetSelected(org.eclipse.swt.events.SelectionEvent event) {
				  SourceViewer viewer = tabViewer.get(tabFolder.getSelection()[0]);
				  CodeDocument doc = (CodeDocument)viewer.getDocument();
				  if(doc.getActiveMethod() == null){
					  recorder.setRecording(false);
					  recordPauseButton.setEnabled(false);
					  recordPauseButton.setText("Start Recording");
				  } else {
					  recordPauseButton.setEnabled(true);
				  }
			  }
		});

		Composite actionsComposite = new Composite(container, SWT.NONE);
		actionsComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		recordPauseButton = new Button(actionsComposite, SWT.PUSH);
		recordPauseButton.setText("Start Recording");
		recordPauseButton.setEnabled(false);
		final Button copyButton = new Button(actionsComposite, SWT.PUSH);
		copyButton.setToolTipText("Copy");
		copyButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));
		
		recordPauseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(recorder.isRecording()){
					recorder.setRecording(false);
					recordPauseButton.setText("Start Recording");
				} else {
					recorder.setRecording(true);
					recordPauseButton.setText("Pause");
				}
			}
		});
		
		this.recorder.addListener(new CodeGenerationListener() {
			public void handleCodeGenerated(List<String> code, String importCode) {
				SourceViewer viewer = tabViewer.get(tabFolder.getSelection()[0]);
				CodeDocument doc = (CodeDocument)viewer.getDocument();
				doc.addImport(importCode);
				for(String c: code){
					doc.addCode(c);
				}
				viewer.setTopIndex(viewer.getBottomIndexEndOffset());
			}
		});
		copyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Clipboard cb = new Clipboard(Display.getCurrent());
				TextTransfer textTransfer = TextTransfer.getInstance();
				SourceViewer viewer = tabViewer.get(tabFolder.getSelection()[0]);
			    cb.setContents(new Object[] {viewer.getDocument().get()}, new Transfer[] { textTransfer });
			    cb.dispose();
			}
		});
		
		addItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createTabItem(tabFolder, className.getText());
				className.setText("");
				recorder.setRecording(false);
				recordPauseButton.setText("Start Recording");
				recordPauseButton.setEnabled(false);
				
			}
		});
		
		className.addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent arg0) {
				if(!className.getText().replace(" ", "").isEmpty()){
					addItem.setEnabled(true);
				} else {
					addItem.setEnabled(false);
				}
				
			}
		});
		
		return container;
	}

	private void createTabItem(final TabFolder tabFolder, String text){
		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(text);
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ToolBar toolBar = new ToolBar(composite, SWT.FLAT);
		RowLayout rLayout = new RowLayout();
		rLayout.fill=true;
		rLayout.justify=true;
		toolBar.setLayout(rLayout);
		ToolItem tItem = new ToolItem(toolBar, SWT.PUSH);
		tItem.setText("Add new method");
		ToolItem tItemDrop = new ToolItem(toolBar, SWT.DROP_DOWN);
		tItemDrop.setText("No active method");
		
		final ToolItem tItemCheck = new ToolItem(toolBar, SWT.CHECK);
		tItemCheck.setText("Mark as Test");
		tItemCheck.setSelection(false);
		
		final DropdownSelectionListener listenerOne = new DropdownSelectionListener(tItemDrop, tItemCheck);
	    tItemDrop.addSelectionListener(listenerOne);
		
		tItem.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				final Shell addMethodShell = new Shell(Display.getCurrent());
				GridLayout gridLayout = new GridLayout();
				gridLayout.numColumns = 2;
				addMethodShell.setLayout(gridLayout);
				addMethodShell.setSize(350, 100);
				addMethodShell.setText("Add new method");
				
				GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
				gridData.horizontalSpan = 1;
				
				final Label label = new Label(addMethodShell, SWT.NONE);
				label.setText("Method name:");
				final Text text = new Text(addMethodShell, SWT.SINGLE | SWT.BORDER);
				text.setLayoutData(gridData);
				
				final Button b = new Button(addMethodShell, SWT.PUSH|SWT.RIGHT);
				b.setText("Add");
				b.setEnabled(false);
				gridData = new GridData(GridData.END, GridData.CENTER, false, false);
				gridData.horizontalSpan = 2;
				b.setLayoutData(gridData);
				addMethodShell.open();
				
				text.addKeyListener(new KeyListener() {
					
					public void keyReleased(KeyEvent arg0) {
						if(!text.getText().isEmpty() && !text.getText().contains(" ")){
							b.setEnabled(true);
						} else {
							b.setEnabled(false);
						}
						
					}

					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				b.addSelectionListener(new SelectionAdapter() {
					
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						SourceViewer viewer = tabViewer.get(tabFolder.getSelection()[0]);
						CodeDocument doc = (CodeDocument)viewer.getDocument();
						doc.addMethod(text.getText());
						listenerOne.add(text.getText());
						tItemCheck.setSelection(false);
						addMethodShell.close();
						recordPauseButton.setEnabled(true);
					}
				});
				
			}
		});
		
		//create editor with syntax coloring
		final CodeDocument doc = new CodeDocument(text);
		final SourceViewer generatedCode = new SourceViewer(composite, new VerticalRuler(0),SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		JavaSourceViewerConfiguration javaConf = 
				new JavaSourceViewerConfiguration(JavaUI.getColorManager(), PreferenceConstants.getPreferenceStore(),
						null, null);
		generatedCode.configure(javaConf);
		JavaTextTools tools = new JavaTextTools(PreferenceConstants.getPreferenceStore());
		tools.setupJavaDocumentPartitioner(doc);
		generatedCode.setDocument(doc);
		generatedCode.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tabItem.setControl(composite);
		tabFolder.setSelection(tabItem);
		tabViewer.put(tabItem, generatedCode);
		tItemCheck.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				doc.markCurrentMethodAsTest(((ToolItem)arg0.widget).getSelection());
			}
		});
	}
	
	@Override
	public void createButtonsForButtonBar(Composite parent) {
		// Override to remove default buttons
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 650);
	}

	class DropdownSelectionListener extends SelectionAdapter {
		  private ToolItem dropdown;
		  private ToolItem check;

		  private Menu menu;

		  public DropdownSelectionListener(ToolItem dropdown, ToolItem check) {
			this.check = check;
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
		        SourceViewer viewer = tabViewer.get(tabFolder.getSelection()[0]);
				CodeDocument doc = (CodeDocument)viewer.getDocument();
		        doc.setActiveMethod(selected.getText());
		        if(doc.getActiveMethod().isTest()){
		        	check.setSelection(true);
		        } else {
		        	check.setSelection(false);
		        }
		      }
		    });
		    dropdown.setText(item);
		  }

		  public void widgetSelected(SelectionEvent event) {
		    if (event.detail == SWT.ARROW) {
		      ToolItem item = (ToolItem) event.widget;
		      Rectangle rect = item.getBounds();
		      Point pt = item.getParent().toDisplay(new Point(rect.x, rect.y));
		      menu.setLocation(pt.x, pt.y + rect.height);
		      menu.setVisible(true);
		    } else {
		      System.out.println(dropdown.getText() + " Pressed");
		    }
		  }
		}
}