package org.jboss.reddeer.eclipse.generator.framework.rules.view.console;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ToolBarRule;

public class ConsoleClearRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	public ConsoleClearRule(){
		rules = new ArrayList<GenerationRule>();
		
		ToolBarRule toolbar = new ToolBarRule();
		toolbar.setToolTipText("Clear Console");
		toolbar.setViewTitle("Console");
		toolbar.setType(ToolBarRule.VIEW);
		
		rules.add(toolbar);
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(rules.size() <= i){
			return false;
		}
		return rules.get(i).equals(rule);
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(rules.size() != this.rules.size()){
			return false;
		}
		for(int i=0;i<rules.size();i++){
			if(!rules.get(i).equals(this.rules.get(i))){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		return null;
	}

	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("consoleView.clear()");
		return toReturn;
	}


	@Override
	public List<String> getImports() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
