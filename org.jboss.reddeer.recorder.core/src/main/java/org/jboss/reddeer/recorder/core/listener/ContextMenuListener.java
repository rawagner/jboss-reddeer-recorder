package org.jboss.reddeer.recorder.core.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

import org.jboss.reddeer.recorder.core.event.ContextMenuRecorderEvent;
import org.jboss.reddeer.recorder.core.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ContextMenuListener extends RecorderListener implements Listener {
	
	public ContextMenuListener(List<RecorderEvent> events, List<MenuItem> menus){
		setEvents(events);
		setMenus(menus);
	}
	
	
	public void handleEvent(Event event) {
		System.out.println("context");
		if (event.widget instanceof Tree) {
				Widget widget = ((Tree) event.widget).getSelection()[0];
				String context_text = ((Tree) event.widget).getSelection()[0].getText();
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
				//ev.setEventIndex(getEvents().size());
				//if(getEvents().get(getEvents().size()-1).equals(ev)){
				//	getEvents().remove(getEvents().size()-1);
				//}

				ContextMenuRecorderEvent contextMenu = new ContextMenuRecorderEvent(widget,context_text);
				TreeRecorderEvent ev = new TreeRecorderEvent(text,index,parents, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
				getEvents().add(ev);
				
				getEvents().add(contextMenu);	
		}
	}


}
