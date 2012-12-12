package org.jboss.reddeer.recorder.core.swt.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class TabFolderRecorderEvent extends RecorderEvent{
	
	private String text;
	
	public TabFolderRecorderEvent(String text, String shell, String view){
		this.text=text;
		setShellName(shell);
		setViewName(view);
	}

	public String getText() {
		return WidgetUtils.convert(text);
	}
	
	public Set<String> start(StringBuilder testBuilder,RecorderEvent previousEvent) {
		testBuilder.append("new DefaultTabItem(\"" + getText() + "\")");
		setOfImports.add(ImportUtils.DEFAULT_TAB_ITEM);
		return setOfImports;
	}

}
