package org.jboss.reddeer.eclipse.generator.framework.rules.view.console;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class ConsoleRule extends AbstractOpenViewRule{
	
	public ConsoleRule(){
		super("General","Console");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new ConsoleClearRule());
		return methods;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("ConsoleView consoleView = new ConsoleView()");
		toReturn.add("consoleView.open()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.console.ConsoleView";
	}



}
