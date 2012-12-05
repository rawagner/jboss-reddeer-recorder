package org.jboss.reddeer.recorder.core.swt.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class CTabFolderRecorderEvent extends RecorderEvent{
	
	private String text;
	
	public CTabFolderRecorderEvent(String text, String shell, String view){
		this.text=text;
		setShellName(shell);
		setViewName(view);
	}

	public String getText() {
		return WidgetUtils.convert(text);
	}
	
	public Set<String> start(StringBuilder testBuilder,RecorderEvent prevEvent) {
		testBuilder.append("bot.cTabFolder(\"" + getText() + "\")");
		return setOfImports;
	}

}
