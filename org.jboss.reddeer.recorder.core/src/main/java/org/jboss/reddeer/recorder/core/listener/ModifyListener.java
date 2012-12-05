package org.jboss.reddeer.recorder.core.listener;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

import org.jboss.reddeer.recorder.core.event.RecorderEvent;

public class ModifyListener extends RecorderListener implements Listener {
	
	public ModifyListener(List<RecorderEvent> events, List<MenuItem> menus){
		setEvents(events);
		setMenus(menus);
	}

	public void handleEvent(Event event) {
		processMenus();

	}

}
