package org.jboss.reddeer.recorder.core.eclipse.generator;

import java.util.List;

import org.jboss.reddeer.recorder.core.eclipse.shell.ShowView;
import org.jboss.reddeer.recorder.core.eclipse.view.ErrorLog;
import org.jboss.reddeer.recorder.core.eclipse.view.PackageExplorer;
import org.jboss.reddeer.recorder.core.swt.event.ActiveEditorRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.MenuRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ShellRecorderEvent;
import org.jboss.reddeer.recorder.core.util.EclipseUtils;

public class GenerateTestEclipse {

	private static final int UNKNOWN = 0;
	private static final int WINDOW_OTHER=1;
	private static final int OPENED=2;

	private int state = UNKNOWN;
	
	public void generate(List<RecorderEvent> events,String testName) {
	
		int returned=0;
		int i=0;
		
		for(RecorderEvent event: events){
			i++;
			if(returned > 0){
				returned--;
			} else if(event instanceof MenuRecorderEvent){
				MenuRecorderEvent menu = (MenuRecorderEvent)event;
				if(menu.getPath().contains("Window") && menu.getPath().contains("Show View")){
					state = OPENED;
					if(menu.getText().equals("Other...")){
						state=WINDOW_OTHER;
					}
				}
			}else if((state == WINDOW_OTHER) && event instanceof ShellRecorderEvent){
				if(((ShellRecorderEvent)event).getName().equals(EclipseUtils.SHOW_VIEW_SHELL)){
					ShowView sw = new ShowView(events.subList(i, events.size()-1));
					returned = sw.generate(new StringBuilder());
					state=OPENED;
				}
			}else if((state == OPENED || state==UNKNOWN) && event instanceof ActiveEditorRecorderEvent){
				if(((ActiveEditorRecorderEvent)event).getText().equals(EclipseUtils.PACKAGE_EXPLORER_WINDOW)){
					PackageExplorer pex = new PackageExplorer(events.subList(i, events.size()-1));
					returned = pex.generate(new StringBuilder());
				}else if(((ActiveEditorRecorderEvent)event).getText().equals(EclipseUtils.ERROR_LOG_WINDOW)){
					ErrorLog er = new ErrorLog(events.subList(i, events.size()-1));
					returned = er.generate(new StringBuilder());
				}
			} else {
				state = UNKNOWN;
			}
		}
	}

}
