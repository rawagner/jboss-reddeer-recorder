package org.jboss.reddeer.eclipse.generator.framework.rules.view.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TreeFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TreeRule;

public class PackageExplorerSelectProject extends GenerationStackRule{

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(!(rule instanceof TreeFilterComplexRule)){
			return false;
		}
		String viewTitle = ((TreeFilterComplexRule)rule).getInitializationRules().get(0).getViewTitle();
		if(viewTitle == null || !viewTitle.equals("Package Explorer")){
			return false;
		}
		return true;
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(rules.size() != 1){
			return false;
		}
		return appliesToPartially(rules.get(0), 0);
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		return null;
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		TreeFilterComplexRule tr = (TreeFilterComplexRule)getInitializationRules().get(0);
		toReturn.add("Project project = packageExplorer.getProject(\""+((TreeRule)tr.getInitializationRules().get(tr.getInitializationRules().size()-1)).getItemText()+"\")");
		toReturn.add("project.select()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.packageexplorer.Project";
	}	
}
