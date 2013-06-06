package org.jboss.reddeer.eclipse.generator.framework.rules.view.errorlog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class ErrorLogRule extends AbstractOpenViewRule{
	
	public ErrorLogRule(){
		super("General","Error Log");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("LogView errorLogView = new LogView()");
		toReturn.add("errorLogView.open()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.views.log.LogView";
	}

	

}
