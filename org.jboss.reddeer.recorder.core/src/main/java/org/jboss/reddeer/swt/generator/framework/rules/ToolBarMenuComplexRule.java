package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;

public class ToolBarMenuComplexRule extends GenerationComplexRule{
	
	private List<GenerationSimpleRule> rules;
	private List<GenerationSimpleRule> initializedRules;
	
	public ToolBarMenuComplexRule(){
		rules = new ArrayList<GenerationSimpleRule>();
		
		ToolBarRule toolBar = new ToolBarRule();
		ContextMenuRule menu = new ContextMenuRule();
		
		rules.add(toolBar);
		rules.add(menu);
	}

	@Override
	public List<GenerationSimpleRule> getInitializationRules() {
		return rules;
		

	}
	
	@Override
	protected String getWidgetAccessor(){
		String parent = ((ToolBarRule)initializedRules.get(0)).getToolTipText();
		
		StringBuilder builder = new StringBuilder();
		builder.append("new ToolbarMenu(");
		builder.append("\""+parent+"\"");
		for(String path: ((ContextMenuRule)initializedRules.get(1)).getPath()){
			builder.append(",\""+path+"\"");
		}
		builder.append(",\""+((ContextMenuRule)initializedRules.get(1)).getMenu()+"\")");
		return builder.toString();
	}
	
	@Override
	protected String getActon(){
		return ".select()";
	}
	

	@Override
	public boolean appliesTo(GenerationSimpleRule rule, int i) {
		return rules.get(i).getClass().equals(rule.getClass()); 
	}
	

	@Override
	public String getRuleName() {
		return "ToolBarMenuStack rule";
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
		ToolBarMenuComplexRule other = (ToolBarMenuComplexRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}

	@Override
	public void initializeForRules(List<GenerationSimpleRule> rules) {
		this.initializedRules=rules;
		
	}

}
