package org.jboss.reddeer.eclipse.generator.framework.rules.view.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ToolBarRule;

public class OutlineCollapseAllRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	public OutlineCollapseAllRule() {
		rules = new ArrayList<GenerationRule>();
		
		
		ToolBarRule toolbar = new ToolBarRule();
		toolbar.setToolTipText("Collapse All");
		toolbar.setViewTitle("Outline");
		toolbar.setType(ToolBarRule.VIEW);
		
		rules.add(toolbar);
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(i >= rules.size()){
			return false;
		}
		if(rule instanceof ToolBarRule){
			ToolBarRule r = (ToolBarRule)rules.get(i);
			return ((ToolBarRule)rule).getToolTipText().contains(r.getToolTipText()) && r.getViewTitle().equals(((ToolBarRule)rule).getViewTitle()) &&
					r.getType() == ((ToolBarRule)rule).getType();
		}
		return false;
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(rules.size() == 1){
			GenerationRule rule = rules.get(0);
			if(rule instanceof ToolBarRule){
				ToolBarRule r = (ToolBarRule)rules.get(0);
				return ((ToolBarRule)rule).getToolTipText().contains(r.getToolTipText()) && r.getViewTitle().equals(((ToolBarRule)rule).getViewTitle()) &&
						r.getType() == ((ToolBarRule)rule).getType();
			}
		}
		return false;
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("outlineView.collapseAll()");
		return toReturn;
	}

	@Override
	public String getImport() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
