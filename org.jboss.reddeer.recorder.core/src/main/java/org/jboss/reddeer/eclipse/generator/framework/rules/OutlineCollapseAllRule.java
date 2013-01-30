package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.ToolBarRule;

public class OutlineCollapseAllRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public String getRuleName() {
		return "OutlineCollapseAllRule";
	}

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		
		ToolBarRule toolbar = new ToolBarRule();
		toolbar.setToolTipText("Collapse All");
		toolbar.setViewName("Outline");
		toolbar.setWorkbenchItem(false);
		
		rules.add(toolbar);
		
		return rules;
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules, Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("outline.collapseAll()");
		return toReturn;
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		if(rule instanceof ToolBarRule){
			ToolBarRule tRule = (ToolBarRule)rule;
			ToolBarRule myRule = (ToolBarRule)rules.get(i);
			return tRule.getToolTipText().contains(myRule.getToolTipText()) 
					&& tRule.getViewName().equals(myRule.getViewName())
					&& tRule.isWorkbenchItem() == tRule.isWorkbenchItem();
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
		OutlineCollapseAllRule other = (OutlineCollapseAllRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	


}
