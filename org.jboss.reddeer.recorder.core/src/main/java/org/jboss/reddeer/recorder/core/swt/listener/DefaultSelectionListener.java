package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class DefaultSelectionListener extends RecorderListener implements Listener{
	
	public DefaultSelectionListener(List<RecorderEvent> events, List<MenuItem> menus){
			setEvents(events);
			setMenus(menus);
	}
	
	public void handleEvent(Event event) {
		System.out.println("defselection");
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
			int index =WidgetUtils.getIndex(event.widget);
			TreeRecorderEvent ev = new TreeRecorderEvent(text,index,WidgetUtils.getSelectedTreeItemIndex((Tree)event.widget,parents),parents, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName(),WidgetUtils.treeHasDuplicatedItems((Tree)event.widget,parents));

			if(getEvents().get(getEvents().size()-1).equals(ev)){
				getEvents().remove(getEvents().size()-1);
			}
			getEvents().add(ev);
		}

	}

}
