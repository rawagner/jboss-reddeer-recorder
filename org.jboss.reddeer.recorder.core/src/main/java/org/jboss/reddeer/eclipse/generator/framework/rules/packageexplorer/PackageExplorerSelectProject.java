package org.jboss.reddeer.eclipse.generator.framework.rules.packageexplorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.TreeRule;

public class PackageExplorerSelectProject extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public String getRuleName() {
		return "ProjectExplorerGetProjectRule";
	}

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		
		TreeRule tree = new TreeRule();
		tree.setViewName("Package Explorer");
		
		rules.add(tree);
		
		return rules;
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules, Set<GenerationStackRule> usedRules) {
		String projectName = ((TreeRule)rules.get(0)).getItemText();
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("projectExplorer.selectProject(\""+projectName+"\")");
		return toReturn;
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		if(rules.get(i).getClass().equals(rule.getClass())){
			return ((TreeRule)rules.get(i)).getViewName().equals(((TreeRule)rule).getViewName());
		}
		return false;
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rules == null) ? 0 : rules.hashCode());
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
		PackageExplorerSelectProject other = (PackageExplorerSelectProject) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	
	

	
	
}
