package org.jboss.reddeer.recorder.core.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormText;

import org.jboss.reddeer.recorder.core.event.ButtonRecorderEvent;
import org.jboss.reddeer.recorder.core.event.CTabFolderRecorderEvent;
import org.jboss.reddeer.recorder.core.event.ComboRecorderEvent;
import org.jboss.reddeer.recorder.core.event.LinkRecorderEvent;
import org.jboss.reddeer.recorder.core.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.event.TabFolderRecorderEvent;
import org.jboss.reddeer.recorder.core.event.TableRecorderEvent;
import org.jboss.reddeer.recorder.core.event.ViewToolBarRecorderEvent;
import org.jboss.reddeer.recorder.core.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.event.WorkbenchToolBarRecorderEvent;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class SelectionListener extends RecorderListener implements Listener{

	
	public SelectionListener(List<RecorderEvent> events, List<MenuItem> menus){
			setEvents(events);
			setMenus(menus);
	}

	public void handleEvent(Event event) {
		System.out.println("selection");
		System.out.println(event.widget.getClass());
		processMenus();

		if (event.widget instanceof Tree) {
			TreeItem item = ((Tree) event.widget).getSelection()[0];
			TreeItem parent = item.getParentItem();
			List<String> parents = new ArrayList<String>();
			while (parent != null) {
				parents.add(parent.getText());
				parent = parent.getParentItem();
			}
			Collections.reverse(parents);
			String text = item.getText();
			int index = WidgetUtils.getIndex(event.widget);
			TreeRecorderEvent ev = new TreeRecorderEvent(text,index,parents, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());

			if(getEvents().get(getEvents().size()-1).equals(ev)){
				getEvents().remove(getEvents().size()-1);
			}
			getEvents().add(ev);
		} else if (event.widget instanceof TabFolder) {
			String text = ((TabFolder)event.widget).getSelection()[0].getText();
			TabFolderRecorderEvent ev = new TabFolderRecorderEvent(text, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(ev);
			
		} else if (event.widget instanceof CTabFolder) {
			String text =((CTabFolder)event.widget).getSelection().getText();
			CTabFolderRecorderEvent ev = new CTabFolderRecorderEvent(text, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(ev);
			
		} else if (event.widget instanceof ToolItem){
			System.out.println("toolIt");
			if(((event.widget.getStyle() & SWT.PUSH) != 0) || ((event.widget.getStyle() & SWT.DROP_DOWN) != 0)){
				int style = event.widget.getStyle();
				String tooltypText = ((ToolItem)event.widget).getToolTipText();
				
				Composite parent = ((ToolItem)event.widget).getParent();
				while(!(parent instanceof CTabFolder) && !(parent instanceof Shell)){
					parent = parent.getParent();
				}
				if(parent instanceof CTabFolder){
					ViewToolBarRecorderEvent viewToolbar = new ViewToolBarRecorderEvent(tooltypText, style, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
					getEvents().add(viewToolbar);
				}
				if(parent instanceof Shell){
					WorkbenchToolBarRecorderEvent workbenchToolBarRecorderEvent = new WorkbenchToolBarRecorderEvent(tooltypText, style, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
					getEvents().add(workbenchToolBarRecorderEvent);
				}
			}
			
		} else if (event.widget instanceof Button) {
			String group = WidgetUtils.getGroup(event.widget);
			String text = ((Button) event.widget).getText();
			int type = ((Button) event.widget).getStyle();
			boolean selected = ((Button) event.widget).getSelection();
			int index = WidgetUtils.getIndex(event.widget);
			ButtonRecorderEvent ev = new ButtonRecorderEvent(text,group,selected,index,type, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(ev);
		} else if (event.widget instanceof Combo) {
			String group = WidgetUtils.getGroup(event.widget);
			String label = WidgetUtils.getLabel(event.widget);
			int index = WidgetUtils.getIndex(event.widget);
			int selection = ((Combo) event.widget).getSelectionIndex();
			ComboRecorderEvent ev = new ComboRecorderEvent(null,label,group,index,selection, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(ev);

		} else if (event.widget instanceof Table) {
			System.out.println("table");
			List<String> listOfStrings = new ArrayList<String>();
			if(!tableHasDuplicates(((Table)event.widget).getItems())){
				for(TableItem item: ((Table)event.widget).getSelection()){
					listOfStrings.add(item.getText());
				}
			}
			int selection[] = ((Table) event.widget).getSelectionIndices();
			int index = WidgetUtils.getIndex(event.widget);
			String group = WidgetUtils.getGroup(event.widget);
			TableRecorderEvent table = new TableRecorderEvent(group, index, selection, listOfStrings, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			if(table.equals(getEvents().get(getEvents().size()-1))){
				getEvents().remove(getEvents().size()-1);
			}
			getEvents().add(table);

		} else if (event.widget instanceof Link) {
			System.out.println("link");
			String text = ((Link)event.widget).getText();
			LinkRecorderEvent ev = new LinkRecorderEvent(text, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());

			getEvents().add(ev);
			//ev.setIndex(WidgetUtils.getIndex(event.widget));

		} else if (event.widget instanceof FormText) {
			System.out.println("formText");
			//((FormText)event.widget).get
			//ev.setIndex(WidgetUtils.getIndex(event.widget));

		}else {
			System.out.println(event.widget.getClass()+" --unsupported selection");
		}
		
	}
	
	private boolean tableHasDuplicates(TableItem tableItems[]){
		Set<String> setOfStrings = new HashSet<String>();
		for(TableItem item: tableItems){
			if(!setOfStrings.add(item.getText())){
				return true;
			}
			
		}
		return false;
	}
}
