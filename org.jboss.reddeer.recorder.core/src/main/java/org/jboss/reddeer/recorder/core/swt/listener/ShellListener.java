package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ShellRecorderEvent;

public class ShellListener extends RecorderListener implements Listener{

	public ShellListener(List<RecorderEvent> events, List<MenuItem> menus){
			setEvents(events);
			setMenus(menus);
	}
	
	
	public void handleEvent(Event event) {
		System.out.println("new shell");
		if (event.widget instanceof Shell) {
			processMenus();
			if (((Shell) event.widget).getText() == null
					|| ((Shell) event.widget).getText().equals("")) {
				return;
			}
			String name = ((Shell) event.widget).getText();
			ShellRecorderEvent shell = new ShellRecorderEvent(name);
			getEvents().add(shell);

		}
	}
	
}
