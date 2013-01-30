package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.ToolBarRule;

public class ConsoleClearRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public String getRuleName() {
		return "ConsoleClearRule";
	}

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		
		ToolBarRule toolbar = new ToolBarRule();
		toolbar.setToolTipText("Clear Console");
		toolbar.setViewName("Console");
		toolbar.setWorkbenchItem(false);
		
		rules.add(toolbar);
		return rules;
		
		
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules,
			Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		if(usedRules.contains(this)){
			toReturn.add("console.clear()");
		}
		return toReturn;
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		if(rule instanceof ToolBarRule){
			System.out.println(((ToolBarRule)rule).getToolTipText());
			System.out.println(((ToolBarRule)rule).getViewName());
			System.out.println(((ToolBarRule)rule).isWorkbenchItem());
		}
		return rules.get(i).equals(rule);
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
		ConsoleClearRule other = (ConsoleClearRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}

	
	

}
