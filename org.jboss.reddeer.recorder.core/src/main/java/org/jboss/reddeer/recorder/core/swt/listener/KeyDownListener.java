package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.List;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import org.jboss.reddeer.recorder.core.swt.event.ComboRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TextRecorderEvent;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class KeyDownListener extends RecorderListener implements Listener{
	
		public KeyDownListener(List<RecorderEvent> events, List<MenuItem> menus){
			setEvents(events);
			setMenus(menus);
		}

		public void handleEvent(Event event) {
			System.out.println("keydown");

			if (event.widget instanceof Text) {
				String text = ((Text) event.widget).getText()+event.character;
				int index = WidgetUtils.getIndex(event.widget);
				String group = WidgetUtils.getGroup(event.widget);
				String label = WidgetUtils.getLabel(event.widget);
				TextRecorderEvent ev = new TextRecorderEvent(text, group, label, index, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
				RecorderEvent e = getEvents().get(getEvents().size()-1);
				if(ev.equals(e)){
					getEvents().remove(getEvents().size()-1);
				}
				getEvents().add(ev);
			} else if (event.widget instanceof Combo) {
				String group = WidgetUtils.getGroup(event.widget);
				String label = WidgetUtils.getLabel(event.widget);
				int index = WidgetUtils.getIndex(event.widget);
				String text = ((Combo) event.widget).getText()+event.character;
				ComboRecorderEvent ev = new ComboRecorderEvent(text,label,group,index,-1, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
				
				RecorderEvent e = getEvents().get(getEvents().size()-1);
				if(ev.equals(e)){
					getEvents().remove(getEvents().size()-1);
				}
				getEvents().add(ev);
			} /*
			else if (event.widget instanceof StyledText) {
				
				ev.setType("StyledText");
				StyledText widgetStyled = (StyledText) event.widget;
				ev.setText(widgetStyled.getText());
				Control[] controls = ((StyledText) event.widget).getParent()
						.getChildren();
				for (int i = 0; i < controls.length; i++) {
					if (controls[i] instanceof Label
							&& controls[i + 1].equals(event.widget)) {
						ev.setLabel(((Label) controls[i]).getText());
					}
				}
				ev.setIndex(WidgetUtils.getIndex(event.widget));
				RecorderEvent e = getEvents().get(getEvents().size()-1);
				if(e.equals(ev)){
					getEvents().remove(getEvents().size()-1);
				}
				getEvents().add(ev);
			}
			*/
		}
		

}
