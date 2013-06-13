package org.jboss.reddeer.eclipse.generator.framework.rules.view.server;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class ServersRule extends AbstractOpenViewRule{
	
	public ServersRule(){
		super("Server","Servers");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("ServersView serversView = new ServersView()");
		toReturn.add("serversView.open()");
		return toReturn;
	}

	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.eclipse.wst.server.ui.view.ServersView");
		return toReturn;
	}

}
