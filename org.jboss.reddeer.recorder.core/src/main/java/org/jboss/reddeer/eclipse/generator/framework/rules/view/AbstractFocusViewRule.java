package org.jboss.reddeer.eclipse.generator.framework.rules.view;

import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.ui.listener.WorkbenchListener;
import org.jboss.reddeer.swt.generator.framework.rules.simple.CTabWorkbenchRule;

public abstract class AbstractFocusViewRule extends GenerationStackRule{
	
	private CTabWorkbenchRule cTabWorkbenchRule;
	
	public AbstractFocusViewRule(String viewTitle){
		cTabWorkbenchRule = new CTabWorkbenchRule();
		cTabWorkbenchRule.setDetail(WorkbenchListener.PART_ACTIVATED);
		cTabWorkbenchRule.setText(viewTitle);
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		return i==0 && rule.equals(cTabWorkbenchRule);
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		return rules.size()==1 && rules.get(0).equals(cTabWorkbenchRule);
	}

	@Override
	public abstract List<GenerationStackRule> getMethods();

	@Override
	public abstract List<String> getActions();

}
