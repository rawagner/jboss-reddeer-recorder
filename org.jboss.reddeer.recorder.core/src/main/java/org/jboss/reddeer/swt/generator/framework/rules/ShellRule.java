package org.jboss.reddeer.swt.generator.framework.rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;

public class ShellRule extends GenerationSimpleRule{
	
	private String shellName;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Shell && event.type == SWT.Activate;
	}

	@Override
	public void initializeForEvent(Event event) {
		Shell shell = (Shell)event.widget;
		this.shellName=shell.getText();
		
	}

	@Override
	protected String getWidgetAccessor() {
		return "new WaitUntil(new ShellWithTextIsActive(\""+shellName+"\"),TimePeriod.NORMAL)";
	}

	@Override
	protected String getActon() {
		return "";
	}

	@Override
	public String getRuleName() {
		return "shell rule";
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((shellName == null) ? 0 : shellName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShellRule other = (ShellRule) obj;
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		return true;
	}

	public String getShellName() {
		return shellName;
	}

	public void setShellName(String shellName) {
		this.shellName = shellName;
	}

}
