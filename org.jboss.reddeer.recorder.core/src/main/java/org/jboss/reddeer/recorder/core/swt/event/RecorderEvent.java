package org.jboss.reddeer.recorder.core.swt.event;

import java.util.HashSet;
import java.util.Set;

public abstract class RecorderEvent {
	
	private String shellName;
	private String viewName;
	protected Set<String> setOfImports =new HashSet<String>();
	public abstract Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent);
	public String getShellName() {
		return shellName;
	}
	public void setShellName(String shellName) {
		this.shellName = shellName;
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
