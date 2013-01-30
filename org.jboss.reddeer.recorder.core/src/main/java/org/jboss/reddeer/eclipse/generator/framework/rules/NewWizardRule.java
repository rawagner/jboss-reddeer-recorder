package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.TreeRule;

public class NewWizardRule extends GenerationStackRule{
	
	private String wizardName;
	private List<GenerationRule> rules;

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		ShellMenuRule mr = new ShellMenuRule();
		List<String> path = new ArrayList<String>();
		path.add("File");
		path.add("New");
		mr.setMenu("Other...");
		mr.setPath(path);
		
		TreeRule tree = new TreeRule();
		tree.setShellName("New");
		
		ButtonRule button = new ButtonRule();
		button.setText("&Next >");
		button.setIndex(1);
		button.setShellName("New");
		button.setStyle(SWT.PUSH);

		rules.add(mr);
		rules.add(tree);
		rules.add(button);
		return rules;
	}
	
	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules,Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		for(GenerationRule rule: rules){
			if(rule instanceof TreeRule){
				wizardName = ((TreeRule)rule).getItemText();
				break;
			}
		}
		toReturn.add("Wizard "+wizardName);
		return toReturn;
	}
	

	@Override
	public String getRuleName() {
		return "new Wizard";
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		if(i == 1){
			return (rule.getClass().equals(rules.get(i).getClass()) && ((TreeRule)rule).getShellName().equals(((TreeRule)rules.get(i)).getShellName()));
		}
		return rule.equals(rules.get(i));
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
		result = prime * result
				+ ((wizardName == null) ? 0 : wizardName.hashCode());
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
		NewWizardRule other = (NewWizardRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		if (wizardName == null) {
			if (other.wizardName != null)
				return false;
		} else if (!wizardName.equals(other.wizardName))
			return false;
		return true;
	}

	
	

}
