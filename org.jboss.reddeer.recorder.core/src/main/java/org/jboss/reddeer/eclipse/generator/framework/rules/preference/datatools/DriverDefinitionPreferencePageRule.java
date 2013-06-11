package org.jboss.reddeer.eclipse.generator.framework.rules.preference.datatools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.preference.AbstractOpenPreferencePage;

public class DriverDefinitionPreferencePageRule extends AbstractOpenPreferencePage{
		
	public DriverDefinitionPreferencePageRule(){
		super("Driver Definitions", "Data Management", "Connectivity");
	}
	
	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("DriverDefinitionPreferencePage() driverDefinitionPreferencePage = new DriverDefinitionPreferencePage()");
		toReturn.add("driverDefinitionPreferencePage.open()");
		return toReturn;
	}
	
	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.datatools.ui.preference.DriverDefinitionPreferencePage";
	}

}
