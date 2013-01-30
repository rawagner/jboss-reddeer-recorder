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

public class TestRule extends GenerationStackRule{
	
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
		
		ButtonRule button1 = new ButtonRule();
		button1.setText("< &Back");
		button1.setIndex(0);
		button1.setShellName("Faceted Project");
		button.setStyle(SWT.PUSH);
		
		
		ButtonRule button2 = new ButtonRule();
		button2.setText("Cancel");
		button2.setIndex(0);
		button2.setShellName("New");
		button.setStyle(SWT.PUSH);
		
		rules.add(mr);
		rules.add(tree);
		rules.add(button);
		rules.add(button1);
		rules.add(button2);
		return rules;
	}
	
	
	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules,Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("test rule");
		return toReturn;
	}
	

	@Override
	public String getRuleName() {
		return "test";
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
		TestRule other = (TestRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	

}
