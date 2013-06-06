package org.jboss.reddeer.eclipse.generator.framework.rules.view.console;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractFocusViewRule;

public class ConsoleFocusViewRule extends AbstractFocusViewRule{

	public ConsoleFocusViewRule() {
		super("Console");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new ConsoleClearRule());
		return methods;
	}

	@Override
	public String getImport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("consoleView.open()");
		return toReturn;
	}

}
