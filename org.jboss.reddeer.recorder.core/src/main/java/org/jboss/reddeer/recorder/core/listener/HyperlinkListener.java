package org.jboss.reddeer.recorder.core.listener;

import java.util.List;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wst.common.project.facet.ui.internal.util.EnhancedHyperlink;

import org.jboss.reddeer.recorder.core.event.HyperlinkRecorderEvent;
import org.jboss.reddeer.recorder.core.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

@SuppressWarnings("restriction")
public class HyperlinkListener extends RecorderListener implements Listener{

	public HyperlinkListener(List<RecorderEvent> events, List<MenuItem> menus){
		setEvents(events);
		setMenus(menus);
	}

	public void handleEvent(Event event) {
		if(event.widget instanceof ImageHyperlink){
			ImageHyperlink link = (ImageHyperlink) event.widget;
			String text = link.getText();
			HyperlinkRecorderEvent hlink = new HyperlinkRecorderEvent(text,WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(hlink);
		} else if(event.widget instanceof EnhancedHyperlink){
			EnhancedHyperlink link = (EnhancedHyperlink) event.widget;
			String text = link.getText();
			HyperlinkRecorderEvent hlink = new HyperlinkRecorderEvent(text, WidgetUtils.getShellName(event.widget),WidgetUtils.getViewName());
			getEvents().add(hlink);
		}
		
	}


}
