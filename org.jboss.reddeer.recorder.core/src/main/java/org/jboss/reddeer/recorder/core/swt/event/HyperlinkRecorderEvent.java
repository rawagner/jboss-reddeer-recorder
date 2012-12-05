package org.jboss.reddeer.recorder.core.swt.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class HyperlinkRecorderEvent extends RecorderEvent{
	
	private String text;
	
	public HyperlinkRecorderEvent(String text, String shell, String view){
		this.text=text;
		setShellName(shell);
		setViewName(view);
	}
	
	public String getText() {
		return WidgetUtils.convert(text);
	}
	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		testBuilder.append("bot.hyperlink(\"" + text + "\")");
		return setOfImports;

	}

}
