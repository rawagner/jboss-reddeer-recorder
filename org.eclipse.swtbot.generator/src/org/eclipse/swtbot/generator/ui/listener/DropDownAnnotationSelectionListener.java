package org.eclipse.swtbot.generator.ui.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.swtbot.generator.ui.BotGeneratorEventDispatcher;
import org.eclipse.swtbot.generator.ui.editor.ClassDocument;

public class DropDownAnnotationSelectionListener extends SelectionAdapter {

	private ToolItem dropDown;
	private Menu menu;
	private Map<CTabItem, SourceViewer> tabViewer;
	private CTabFolder classTabFolder;

	public DropDownAnnotationSelectionListener(ToolItem dropdown, BotGeneratorEventDispatcher recorder, Map<CTabItem, SourceViewer> tabViewer, CTabFolder classTabFolder) {
		this.dropDown=dropdown;
		this.menu = new Menu(dropdown.getParent());
		this.tabViewer = tabViewer;
		this.classTabFolder = classTabFolder;
		addItems(recorder.getCurrentGenerator().createAnnotationRules());
	}

	public void addItems(List<AnnotationRule> items) {
		menu = new Menu(dropDown.getParent());
		if(items != null){
			List<AnnotationRule> methodAnnotations = new ArrayList<AnnotationRule>();
			for(AnnotationRule item: items){
				if(!item.isClassAnnotation()){
					methodAnnotations.add(item);
				}
			}
			if(!methodAnnotations.isEmpty()){
				for (AnnotationRule item : methodAnnotations) {
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
								doc.addAnnotation((AnnotationRule) menuItem.getData());
							} else {
								doc.removeAnnotation((AnnotationRule) menuItem.getData());
							}
							update();
						}
					});
				}
			}
		}
		update();
	}

	public void update() {
		SourceViewer viewer = null;
		if(classTabFolder != null && tabViewer != null){
			viewer = tabViewer.get(classTabFolder.getSelection());
		}
		if(viewer != null){
			ClassDocument doc = (ClassDocument) viewer.getDocument();
			if(doc.getActiveMethod() != null && menu.getItems().length!=0){
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
			dropDown.setEnabled(false);
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
