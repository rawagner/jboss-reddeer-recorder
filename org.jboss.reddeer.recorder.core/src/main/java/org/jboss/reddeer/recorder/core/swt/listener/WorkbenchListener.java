package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.List;

import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

import org.jboss.reddeer.recorder.core.swt.event.ActiveEditorRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;

public class WorkbenchListener extends RecorderListener implements IPartListener2{
	
	public WorkbenchListener(List<RecorderEvent> events, List<MenuItem> menus){
		setEvents(events);
		setMenus(menus);
	}
	
	public void partActivated(IWorkbenchPartReference ref) {
		processMenus();
		String text = ref.getTitle();
		ActiveEditorRecorderEvent ev = new ActiveEditorRecorderEvent(text);
		getEvents().add(ev);
	}

	public void partBroughtToTop(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}

	public void partClosed(IWorkbenchPartReference ref) {
		System.out.println(ref.getTitle()+ " closed");
		
	}

	public void partDeactivated(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}

	public void partHidden(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}

	public void partInputChanged(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}

	public void partOpened(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}

	public void partVisible(IWorkbenchPartReference arg0) {
		// TODO Auto-generated method stub
		
	}


}
