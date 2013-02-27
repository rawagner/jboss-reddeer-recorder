package org.jboss.reddeer.eclipse.generator.framework.rules.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.TreeRule;

public class ConsoleRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		ShellMenuRule mr = new ShellMenuRule();
		List<String> path = new ArrayList<String>();
		path.add("Window");
		path.add("Show View");
		mr.setPath(path);
		mr.setMenu("Other...");
		
		ShellRule shell = new ShellRule();
		shell.setShellName("Show View");
		
		ButtonRule br = new ButtonRule();
		br.setGroup(null);
		br.setIndex(1);
		br.setText("OK");
		br.setShellName("Show View");
		br.setStyle(SWT.PUSH);
		
		TreeRule tr = new TreeRule();
		tr.setIndex(0);
		tr.setItemText("Console");
		tr.setShellName("Show View");
		List<String> parents = new ArrayList<String>();
		parents.add("General");
		tr.setParents(parents);
		
		rules.add(mr);
		rules.add(shell);
		rules.add(tr);
		rules.add(br);
		
		return rules;
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules, Set<GenerationStackRule> usedRules) {
		List<String> toReturn = new ArrayList<String>();
		for(GenerationStackRule r: usedRules){
			System.out.println(r.getRuleName());
			System.out.println(usedRules.contains(this));
			
		}
		if(!usedRules.contains(this)){
			toReturn.add("Console console = new Console()");
		}
		toReturn.add("console.open()");
		return toReturn;
	}

	@Override
	public String getRuleName() {
		return "console";
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
		ConsoleRule other = (ConsoleRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	

}
