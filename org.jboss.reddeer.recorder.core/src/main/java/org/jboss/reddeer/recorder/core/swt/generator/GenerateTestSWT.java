package org.jboss.reddeer.recorder.core.swt.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;

import org.jboss.reddeer.recorder.core.swt.event.ActiveEditorRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ButtonRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.CTabFolderRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ComboRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ContextMenuRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.HyperlinkRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.LinkRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.MenuRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ShellRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TabFolderRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TableRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TextRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ViewToolBarRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.WorkbenchToolBarRecorderEvent;
import org.jboss.reddeer.recorder.core.util.ImportUtils;

public class GenerateTestSWT {

	public boolean reverseContextTreeHack = false;
	private Set<String> setOfImports = new HashSet<String>();

	public void generate(List<RecorderEvent> events,String testName) {
		events.remove(events.size()-1); //remove click on stop button
		StringBuilder testBuilder = new StringBuilder();
		createNewTest(testBuilder,testName);
		RecorderEvent previousEvent = null;
		
		for (RecorderEvent e : events) {
			test(e);
			if (e instanceof ButtonRecorderEvent) {
				ButtonRecorderEvent button = (ButtonRecorderEvent) e;
				endPrevious(testBuilder, button, previousEvent);
				Set<String> imports = button.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = button;
			} else if (e instanceof MenuRecorderEvent) {
				MenuRecorderEvent menu = (MenuRecorderEvent) e;
				boolean hack = endPrevious(testBuilder, menu, previousEvent);
				menu.setReverseContextTreeHack(hack);
				Set<String> imports = menu.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = menu;
			} else if (e instanceof ShellRecorderEvent) {
				ShellRecorderEvent shell = (ShellRecorderEvent) e;
				endPrevious(testBuilder, shell, previousEvent);
				Set<String> imports = shell.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = shell;
			} else if (e instanceof TextRecorderEvent) {
				TextRecorderEvent text = (TextRecorderEvent) e;
				endPrevious(testBuilder, text, previousEvent);
				Set<String> imports = text.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = text;
			} else if (e instanceof TreeRecorderEvent) {
				TreeRecorderEvent tree = (TreeRecorderEvent) e;
				endPrevious(testBuilder, tree, previousEvent);
				tree.setReverseContextTreeHack(reverseContextTreeHack);
				Set<String> imports = tree.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = tree;
			} else if (e instanceof ContextMenuRecorderEvent) {
				ContextMenuRecorderEvent contextMenu = (ContextMenuRecorderEvent) e;
				endPrevious(testBuilder, contextMenu, previousEvent);
				previousEvent = contextMenu;
			} else if (e instanceof ActiveEditorRecorderEvent) {
				ActiveEditorRecorderEvent activeEditor = (ActiveEditorRecorderEvent) e;
				endPrevious(testBuilder, activeEditor, previousEvent);
				Set<String> imports = activeEditor.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = activeEditor;
			} else if (e instanceof ViewToolBarRecorderEvent) {
				ViewToolBarRecorderEvent toolbar = (ViewToolBarRecorderEvent) e;
				endPrevious(testBuilder, toolbar, previousEvent);
				Set<String> imports = toolbar.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = toolbar;
			} else if (e instanceof WorkbenchToolBarRecorderEvent) {
				WorkbenchToolBarRecorderEvent toolbar = (WorkbenchToolBarRecorderEvent) e;
				endPrevious(testBuilder, toolbar, previousEvent);
				Set<String> imports = toolbar.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = toolbar;
			} else if (e instanceof CTabFolderRecorderEvent) {
				CTabFolderRecorderEvent ctab = (CTabFolderRecorderEvent) e;
				endPrevious(testBuilder, ctab, previousEvent);
				Set<String> imports = ctab.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = ctab;
			} else if (e instanceof TabFolderRecorderEvent) {
				TabFolderRecorderEvent tab = (TabFolderRecorderEvent) e;
				endPrevious(testBuilder, tab, previousEvent);
				Set<String> imports = tab.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = tab;
			} else if (e instanceof TableRecorderEvent) {
				TableRecorderEvent table = (TableRecorderEvent) e;
				endPrevious(testBuilder, table, previousEvent);
				Set<String> imports = table.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = table;
			} else if (e instanceof ComboRecorderEvent) {
				ComboRecorderEvent combo = (ComboRecorderEvent) e;
				endPrevious(testBuilder, combo, previousEvent);
				Set<String> imports = combo.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = combo;
			} else if (e instanceof HyperlinkRecorderEvent) {
				HyperlinkRecorderEvent link = (HyperlinkRecorderEvent) e;
				endPrevious(testBuilder, link, previousEvent);
				Set<String> imports = link.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = link;
			} else if (e instanceof LinkRecorderEvent) {
				LinkRecorderEvent link = (LinkRecorderEvent) e;
				endPrevious(testBuilder, link, previousEvent);
				Set<String> imports = link.start(testBuilder, previousEvent);
				setOfImports.addAll(imports);
				previousEvent = link;
			}

		}
		endPrevious(testBuilder, null, previousEvent);
		endNewTest(testBuilder);
		StringBuilder source = addImports(setOfImports, testBuilder);
		format(source);
	}

	private StringBuilder addImports(Set<String> imports, StringBuilder source){
		StringBuilder importBuilder = new StringBuilder();
		for(String i : imports){
			importBuilder.append(i);
		}
		importBuilder.append(source);
		return importBuilder;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	private void format(StringBuilder testBuilder) {
		System.out.println(testBuilder.toString());
		
		Map options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM,
				JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		final CodeFormatter codeFormatter = ToolFactory
				.createCodeFormatter(options);

		String source = testBuilder.toString();
		final TextEdit edit = codeFormatter.format(
				CodeFormatter.K_UNKNOWN, source, 0,
				source.length(), 0, System.getProperty("line.separator"));
		IDocument document = new Document(source);
		try {
			edit.apply(document);
		} catch (MalformedTreeException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		File file = new File("test");
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(document.get());
			fileWriter.flush();
			fileWriter.close();
			System.out.println("test generated at: "+file.getAbsoluteFile());
		} catch (IOException e) {
			System.err.println("Could not save formatted file");
			e.printStackTrace();
		}

	}

	private void createNewTest(StringBuilder testBuilder,String testName) {
		testBuilder.append("public class NewTestClass{");
		testBuilder.append("@Test ");
		testBuilder.append("public void "+testName+"(){");
		setOfImports.add(ImportUtils.JUNIT_TEST);
	}

	private void endNewTest(StringBuilder testBuilder) {
		testBuilder.append("}");
		testBuilder.append("}");
	}
	
	private boolean endPrevious(StringBuilder testBuilder, RecorderEvent currentEvent, RecorderEvent previousEvent) {
		if(previousEvent == null){
			return false;
		}else if (currentEvent instanceof TreeRecorderEvent	&& previousEvent instanceof ContextMenuRecorderEvent) {
			TreeRecorderEvent treeEvent = (TreeRecorderEvent) currentEvent;
			ContextMenuRecorderEvent contextEvent = (ContextMenuRecorderEvent) previousEvent;
			if (contextEvent.getWidget() instanceof TreeItem) {
				if (!((contextEvent.getText()).equals(treeEvent.getText()))) {
					testBuilder.append(".select();");
				} else {
					reverseContextTreeHack = true;
				}
			}
		} else if (reverseContextTreeHack && currentEvent instanceof MenuRecorderEvent && previousEvent instanceof TreeRecorderEvent) {
			reverseContextTreeHack = false;
			return true;
		} else if (previousEvent instanceof ButtonRecorderEvent && (((ButtonRecorderEvent) previousEvent).getType() & SWT.CHECK) != 0) {
			if (((ButtonRecorderEvent) previousEvent).isSelected()) {
				testBuilder.append(".toggle(true);");
			} else {
				testBuilder.append(".toggle(false);");
			}
		} else if(previousEvent instanceof TreeRecorderEvent || previousEvent instanceof MenuRecorderEvent){
			testBuilder.append(".select();");
		} else if(previousEvent instanceof ButtonRecorderEvent 
				|| previousEvent instanceof ViewToolBarRecorderEvent
				|| previousEvent instanceof WorkbenchToolBarRecorderEvent){
			testBuilder.append(".click();");
		} else if(previousEvent instanceof ContextMenuRecorderEvent){
			return false;
		} else {
			testBuilder.append(";");
		}
		return false;

	}

	public void test(RecorderEvent e) {
		if (e instanceof ButtonRecorderEvent) {
			System.out.println("Button"+e.getShellName()+e.getViewName());
		} else if (e instanceof ContextMenuRecorderEvent) {
			System.out.println("Context"+e.getShellName()+e.getViewName());
		} else if (e instanceof MenuRecorderEvent) {
			System.out.println("Menu"+e.getShellName()+e.getViewName());
		} else if (e instanceof ShellRecorderEvent) {
			System.out.println("Shell"+e.getShellName()+e.getViewName());
		} else if (e instanceof TextRecorderEvent) {
			System.out.println("text"+e.getShellName()+e.getViewName());
		} else if (e instanceof TreeRecorderEvent) {
			System.out.println("tree"+e.getShellName()+e.getViewName());
		} else if (e instanceof ActiveEditorRecorderEvent) {
			System.out.println("activeEditor"+e.getShellName()+e.getViewName());
		}
	}

}
