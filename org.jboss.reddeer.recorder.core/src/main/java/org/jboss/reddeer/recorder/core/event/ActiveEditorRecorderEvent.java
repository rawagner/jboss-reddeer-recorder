package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

public class ActiveEditorRecorderEvent extends RecorderEvent {

	private String text;
	
	public ActiveEditorRecorderEvent(String text){
		this.text=text;
	}

	public String getText() {
		return text;
	}
	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		testBuilder.append("bot.viewByTitle(\"" + getText() + "\")");
		return setOfImports;
	}

}
