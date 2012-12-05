package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ShellRecorderEvent extends RecorderEvent {

	private String name;
	
	public ShellRecorderEvent(String name){
		this.name=name;
	}

	public String getName() {
		return WidgetUtils.convert(name);
	}
	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		testBuilder.append("new WaitUntil(new ShellWithTextIsActive(\"" + name + "\"),TimePeriod.NORMAL)");
		setOfImports.add(ImportUtils.WAIT_UNTIL);
		setOfImports.add(ImportUtils.SHELL_WITH_TEXT_IS_ACTIVE);
		setOfImports.add(ImportUtils.TIME_PERIOD);
		return setOfImports;

	}

}
