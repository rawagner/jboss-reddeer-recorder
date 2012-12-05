package org.jboss.reddeer.recorder.core.eclipse;

import java.util.List;

import org.jboss.reddeer.recorder.core.event.ActiveEditorRecorderEvent;
import org.jboss.reddeer.recorder.core.event.ButtonRecorderEvent;
import org.jboss.reddeer.recorder.core.event.ContextMenuRecorderEvent;
import org.jboss.reddeer.recorder.core.event.MenuRecorderEvent;
import org.jboss.reddeer.recorder.core.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.event.ShellRecorderEvent;
import org.jboss.reddeer.recorder.core.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.util.EclipseUtils;

public class ShowView {
	

	public static final int OPENED=0;

	
	private List<RecorderEvent> events;
	
	public ShowView(List<RecorderEvent> events){
		this.events=events;
	}

	public int generate(StringBuilder builder){
		System.out.println("generating Show View");
		int i=0;
		for(RecorderEvent event: events){
			if(!((event instanceof ShellRecorderEvent) 
					|| (event instanceof ActiveEditorRecorderEvent) 
					|| (event instanceof ContextMenuRecorderEvent)
					|| (event instanceof MenuRecorderEvent))
					&& !event.getViewName().equals(EclipseUtils.SHOW_VIEW_SHELL)){
				break;
			} else if(event instanceof ButtonRecorderEvent){
				i++;
				return i;
			}
			i++;
		}
		return i;
	}

}
