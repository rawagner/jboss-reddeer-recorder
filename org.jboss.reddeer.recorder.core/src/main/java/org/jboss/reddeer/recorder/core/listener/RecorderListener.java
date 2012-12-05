package org.jboss.reddeer.recorder.core.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import org.jboss.reddeer.recorder.core.event.MenuRecorderEvent;
import org.jboss.reddeer.recorder.core.event.RecorderEvent;

public abstract class RecorderListener {
	
	private List<RecorderEvent> events;
	private List<MenuItem> menus;
	public List<RecorderEvent> getEvents() {
		return events;
	}
	public void setEvents(List<RecorderEvent> events) {
		this.events = events;
	}
	public List<MenuItem> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuItem> menus) {
		this.menus = menus;
	}
	
	protected void processMenus(){
		if (!menus.isEmpty()) {
			System.out.println("Processing Menus");
			MenuItem item = menus.get(menus.size() - 1);
			Menu par = item.getParent();
			MenuItem parent = par.getParentItem();
			List<String> parents = new ArrayList<String>();
			String text = item.getText();
			//parents.add(ev);
			while (parent != null) {
				parents.add(parent.getText());
				Menu p = parent.getParent();
				parent = p.getParentItem();
				/*
				ev = new MenuRecorderEvent();
				ev.setText(parent.getText());
				parents.add(ev);
				Menu p = parent.getParent();
				parent = p.getParentItem();
				*/

			}
			Collections.reverse(parents);
			MenuRecorderEvent ev = new MenuRecorderEvent(text,parents);
			//getEvents().addAll(parents);
			events.add(ev);
			getMenus().clear();
		}
	}
	
}
