package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.ContextMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.TreeRule;

public class PackageExplorerDeleteProjectOnDiskRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;

	@Override
	public String getRuleName() {
		return "ProjectExplorerGetProjectRule";
	}

	@Override
	public List<GenerationRule> getInitializationRules() {
		rules = new ArrayList<GenerationRule>();
		
		TreeRule tree = new TreeRule();
		tree.setViewName("Package Explorer");
		
		ContextMenuRule cm = new ContextMenuRule();
		cm.setMenu("Delete");
		cm.setPath(new ArrayList<String>());
		
		ShellRule sm = new ShellRule();
		sm.setShellName("Delete Resources");
		
		ButtonRule button = new ButtonRule();
		button.setStyle(SWT.CHECK);
		button.setGroup(null);
		button.setIndex(0);
		button.setShellName("Delete Resources");
		button.setText("&Delete project contents on disk (cannot be undone)");
		button.setToggle(true);
		
		ButtonRule button1 = new ButtonRule();
		button1.setStyle(SWT.PUSH);
		button1.setGroup(null);
		button1.setIndex(2);
		button1.setShellName("Delete Resources");
		button1.setText("OK");
		
		rules.add(tree);
		rules.add(cm);
		rules.add(sm);
		rules.add(button);
		rules.add(button1);
		
		return rules;
	}

	@Override
	public List<String> generateInitializationPhase(List<GenerationRule> rules, Set<GenerationStackRule> usedRules) {
		String projectName = ((TreeRule)rules.get(0)).getItemText();
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("projectExplorer.getProject(\""+projectName+"\").delete(true)");
		return toReturn;
	}

	@Override
	public boolean appliesTo(GenerationRule rule, int i) {
		if(i==0){
			if(rules.get(i).getClass().equals(rule.getClass())){
				return ((TreeRule)rules.get(i)).getViewName().equals(((TreeRule)rule).getViewName());
			}
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
		PackageExplorerDeleteProjectOnDiskRule other = (PackageExplorerDeleteProjectOnDiskRule) obj;
		if (rules == null) {
			if (other.rules != null)
				return false;
		} else if (!rules.equals(other.rules))
			return false;
		return true;
	}
	
	

}
