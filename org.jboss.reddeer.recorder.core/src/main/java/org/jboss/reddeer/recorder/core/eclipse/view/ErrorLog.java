package org.jboss.reddeer.recorder.core.eclipse.view;

import java.util.List;

import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;

public class ErrorLog {
	
	public static final int OPENED=0;

	
	private List<RecorderEvent> events;

	public ErrorLog(List<RecorderEvent> events){
		this.events=events;
	}

	public int generate(StringBuilder builder){
		builder.append("ErrorLog errorLog = new ErrorLog();");
		builder.append("errorLog.open();");
		System.out.println(builder.toString());
		return 0;
	}
}
