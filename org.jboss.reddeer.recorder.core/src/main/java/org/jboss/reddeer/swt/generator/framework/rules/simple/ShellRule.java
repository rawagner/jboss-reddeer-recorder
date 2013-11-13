package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerUtils;

public class ShellRule extends GenerationSimpleRule{
	
	private int shellAction;
	

	@Override
	public boolean appliesTo(Event event) {
		if(event.widget instanceof Shell && event.type == SWT.Activate){
			if(RedDeerUtils.getWorkbench() != null){
				return !RedDeerUtils.getWorkbench().getText().equals(((Shell)event.widget).getText());
			}
			return true;
		} 
		return event.widget instanceof Shell && event.type == SWT.Close;
	}

	@Override
	public void initializeForEvent(Event event) {
		shellAction = event.type;
		setShellTitle(((Shell)event.widget).getText());
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append("new DefaultShell(\""+getShellTitle()+"\")");
		if(shellAction == SWT.Close){
			builder.append(".close()");
		}
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.shell.DefaultShell");
		return toReturn;
	}
	
	public int getShellAction() {
		return shellAction;
	}

	public void setShellAction(int shellAction) {
		this.shellAction = shellAction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + shellAction;
		result = prime * result
				+ ((getShellTitle() == null) ? 0 : getShellTitle().hashCode());
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
		if (shellAction != other.shellAction)
			return false;
		if (getShellTitle() == null) {
			if (other.getShellTitle() != null)
				return false;
		} else if (!getShellTitle().equals(other.getShellTitle()))
			return false;
		return true;
	}

}
