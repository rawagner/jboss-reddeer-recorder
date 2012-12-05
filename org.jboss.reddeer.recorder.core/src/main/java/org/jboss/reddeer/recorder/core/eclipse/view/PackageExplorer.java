package org.jboss.reddeer.recorder.core.eclipse.view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.recorder.core.swt.event.ActiveEditorRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ButtonRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ContextMenuRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.MenuRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.ShellRecorderEvent;
import org.jboss.reddeer.recorder.core.swt.event.TreeRecorderEvent;
import org.jboss.reddeer.recorder.core.util.EclipseUtils;

public class PackageExplorer {
	
	public static final int OPENED=0;
	public static final int STATE_SELECTED_PROJECT=1;
	public static final int DELETING=2;
	public static final int DELETING_F=3;
	public static final int DELETING_T=4;
		
	private List<RecorderEvent> events;
	private int state;
	
	public PackageExplorer(List<RecorderEvent> events){
		this.events = events;
		state = OPENED;
	}
	
	public int generate(StringBuilder builder){
		int used=0;
		builder.append("PackageExplorer packageExplorer = new PackageExplorer();");
		builder.append("packageExplorer.open();");
		for(RecorderEvent event:events){
			if(!((event instanceof ShellRecorderEvent) 
					|| (event instanceof ActiveEditorRecorderEvent) 
					|| (event instanceof ContextMenuRecorderEvent)
					|| (event instanceof MenuRecorderEvent))
					&& !event.getViewName().equals(EclipseUtils.PACKAGE_EXPLORER_WINDOW)){
				break;
			}
			used++;
			if(state == OPENED){
				if(event instanceof TreeRecorderEvent){
					List<String> parents = ((TreeRecorderEvent)event).getParents();
					builder.append("Project project = ");
					if(parents.isEmpty()){
						builder.append("packageExplorer.select(\""+((TreeRecorderEvent)event).getText()+"\");");
					}else{
						builder.append("packageExplorer.select(\""+parents.get(0)+"\");");
					}
					state=STATE_SELECTED_PROJECT;
				} else {
					break;
				}
			} else if(state == STATE_SELECTED_PROJECT){
				if(event instanceof ContextMenuRecorderEvent){
					
				}
				else if(event instanceof MenuRecorderEvent){
					
				}
				else if(event instanceof ShellRecorderEvent){
					if(((ShellRecorderEvent)event).getName().equals("Delete Resources")){
						state=DELETING;
				} else {
					break;
				}
			} else if(state == DELETING)	
				if(event instanceof ButtonRecorderEvent){
					if((((ButtonRecorderEvent)event).getType() & SWT.CHECK) != 0){
						state=DELETING_T;
					} else {
						state=DELETING;
					}
				} else {
					break;
				}
			} else if(state == DELETING || state == DELETING_T){
				if(event instanceof ButtonRecorderEvent){
					if(((ButtonRecorderEvent)event).getText().equals("OK")){
						if(state == DELETING){
							builder.append("project.delete(false);");
						} else {
							builder.append("project.delete(true);");
						}
						state = OPENED;
					}
				} else {
					break;
				}
			}
		}
		System.out.println(builder.toString());
		return used;
	}
}
