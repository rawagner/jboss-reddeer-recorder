/*******************************************************************************
 * Copyright (c) 2012 Red Hat Inc..
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Mickael Istria (Red Hat) - initial API and implementation
 *    Rastislav Wagner (Red Hat) - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.generator.framework;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Section;

public class WidgetUtils {

	public static int getIndex(Control control) {
		// This is the reverse method of
		//    Matcher matcher = allOf(widgetOfType(Button.class), withStyle(SWT.PUSH, "SWT.PUSH"));
		//    return new SWTBotButton((Button) widget(matcher, index), matcher);
		// TODO? Evaluate reusing matchers here too

		int index = 0;
		Composite parent = null;
		do {
			parent = control.getParent();
			for (Control c : parent.getChildren()) {
				if(c.equals(control)){
					return index;
				} else if (c.getClass().equals(control.getClass())){
					index++;
				}
			}
		} while(!(parent instanceof Shell));

		throw new RuntimeException("Could not determine index for widget " + control);
	}
	
	public static String getGroup(Control control){
		Composite parent = control.getParent();
		while(parent != null){
			if(parent instanceof Group){
				return WidgetUtils.cleanText(((Group)parent).getText());
			}
			parent = parent.getParent();
		}

		return null;
	}
	
	public static Shell getShell(Control widget) {
		while (widget != null) {
			if (widget instanceof Shell) {
				return ((Shell)widget);
			} else {
				widget = ((Control)widget).getParent();
			}
		}
		return null;
	}
	
	public static CTabItem getView(Control widget) {
		while (widget != null) {
			if (widget instanceof CTabFolder) {
				return ((CTabFolder)widget).getSelection();
			} else {
				widget = ((Control)widget).getParent();
			}
		}
		return null;
	}

	public static String getLabel(Control widget) {
		Control[] controls = widget.getParent().getChildren();
		for (int i = 0; i < controls.length; i++) {
			if (controls[i] instanceof Label && controls[i + 1].equals(widget)) {
				return WidgetUtils.cleanText(((Label) controls[i]).getText());
			}
		}
		return null;
	}
	
	public static String cleanText(String text) {
		if(text	!= null){
			return text.replaceAll("&", "").split("\t")[0];
		} 
		return null;
	}
	
	public static Shell getWorkbench(){
		if(PlatformUI.getWorkbench().getActiveWorkbenchWindow()!=null){
			return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		}
		return null;
	}
	
	public static Section getSection(Control widget) {
		while (widget != null) {
			if (widget instanceof Section) {
				return (Section)widget;
			} else {
				widget = widget.getParent();
			}
		}
		return null;
	}
	
	public static String getCurrentWorkbenchViewTitle(){
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getTitle();
	}


}