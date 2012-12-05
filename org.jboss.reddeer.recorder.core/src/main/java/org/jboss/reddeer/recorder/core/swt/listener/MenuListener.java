package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;

import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;

public class MenuListener extends RecorderListener implements Listener{

	public MenuListener(List<RecorderEvent> events, List<MenuItem> menus){
		setEvents(events);
		setMenus(menus);
	}

	public void handleEvent(Event event) {
		System.out.println("menu");
		if (event.widget instanceof MenuItem) {
			getMenus().add((MenuItem) event.widget);
		}

	}
}
