package org.jboss.reddeer.eclipse.generator.framework.rules.preference.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.preference.AbstractOpenPreferencePage;

public class RuntimePreferencePageRule extends AbstractOpenPreferencePage{
	
	public RuntimePreferencePageRule(){
		super("Runtime Environments","Server");
	}
	
	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("RuntimePreferencePage runtimePreferencePage = new RuntimePreferencePage()");
		toReturn.add("runtimePreferencePage.open()");
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage");
		return toReturn;
	}
}
