package org.jboss.reddeer.eclipse.generator.framework.rules.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.console.ConsoleClearRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.TreeRule;

public class RuntimePreferencePageRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		ShellMenuRule mr = new ShellMenuRule();
		List<String> path = new ArrayList<String>();
		path.add("Window");
		mr.setPath(path);
		mr.setMenu("Preferences");
		
		ShellRule shell = new ShellRule();
		shell.setShellName("Preferences");
		
		TreeRule tr = new TreeRule();
		tr.setIndex(0);
		tr.setItemText("Runtime Environments");
		tr.setShellName("Preferences");
		List<String> parents = new ArrayList<String>();
		parents.add("Server");
		tr.setParents(parents);
		
		rules.add(mr);
		rules.add(shell);
		rules.add(tr);
		
		return rules;
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules, Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		if(!usedRules.contains(this)){
			toReturn.add("RuntimePreferencePage runtimePreferencePage = new RuntimePreferencePage()");
		}
		toReturn.add("runtimePreferencePage.open()");
		return toReturn;
	}

	@Override
	public String getRuleName() {
		return "RuntimePreferencePage";
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		return rule.equals(rules.get(i));
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new ConsoleClearRule());
		return methods;
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
		RuntimePreferencePageRule other = (RuntimePreferencePageRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	
	


}
