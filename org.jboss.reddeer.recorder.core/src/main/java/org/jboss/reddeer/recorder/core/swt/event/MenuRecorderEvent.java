package org.jboss.reddeer.recorder.core.swt.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.TreeItem;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class MenuRecorderEvent extends RecorderEvent {

	private List<String> path;
	private String text;
	private boolean reverseContextTreeHack = false;
	
	public MenuRecorderEvent(String text,List<String> path){
		this.text=text;
		this.path=path;
	}


	public String getText() {
		return WidgetUtils.convert(text);
	}
	
	
	public boolean isReverseContextTreeHack() {
		return reverseContextTreeHack;
	}

	public void setReverseContextTreeHack(boolean hack) {
		this.reverseContextTreeHack = hack;
	}
	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if (previousEvent != null && previousEvent instanceof ContextMenuRecorderEvent){
			if (((ContextMenuRecorderEvent) previousEvent).getWidget() instanceof TreeItem) {
				testBuilder.append("new ContextMenu(");
				setOfImports.add(ImportUtils.CONTEXT_MENU);
				for(String parent: getPath()){
					testBuilder.append("\""+parent+"\",");
				}
				testBuilder.append("\""+getText()+"\")");
			}
		} else if (previousEvent != null && reverseContextTreeHack	&& previousEvent instanceof TreeRecorderEvent) {
			testBuilder.append("new ContextMenu(");
			setOfImports.add(ImportUtils.CONTEXT_MENU);
			for(String parent: getPath()){
				testBuilder.append("\""+parent+"\",");
			}
			testBuilder.append("\""+getText()+"\")");
		
		
		
		
		} else {
			testBuilder.append("new ShellMenu(");
			setOfImports.add(ImportUtils.SHELL_MENU);
			for(String parent: getPath()){

				testBuilder.append("\""+parent+"\",");
			}
			testBuilder.append("\""+getText()+"\")");
		}
		return setOfImports;
	}

	public List<String> getPath() {
		List<String> pathConverted = new ArrayList<String>();
		for(String s:path){
			pathConverted.add(WidgetUtils.convert(s));
		}
		return pathConverted;
	}

}
