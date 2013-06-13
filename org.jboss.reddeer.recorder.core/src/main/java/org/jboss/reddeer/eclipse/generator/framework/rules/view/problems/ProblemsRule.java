package org.jboss.reddeer.eclipse.generator.framework.rules.view.problems;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class ProblemsRule extends AbstractOpenViewRule{
	
	public ProblemsRule(){
		super("General","Problems");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	


	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("ProblemsView problemsView = new ProblemsView()");
		toReturn.add("problemsView.open()");
		return toReturn;
	}
	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.eclipse.ui.problems.ProblemsView");
		return toReturn;
	}
}
