package org.jboss.reddeer.eclipse.generator.framework.rules.runtime;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TreeFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TreeRule;

public class RuntimePreferencePageRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;
	
	public RuntimePreferencePageRule(){
		rules = new ArrayList<GenerationRule>();
		
		ShellMenuRule mr = new ShellMenuRule();
		List<String> path = new ArrayList<String>();
		path.add("Window");
		mr.setPath(path);
		mr.setMenu("Preferences");
		
		ShellRule sr = new ShellRule();
		sr.setShellTitle("Preferences");
		sr.setShellAction(SWT.Activate);

		TreeRule tr = new TreeRule();
		tr.setTreeIndex(0);
		tr.setItemText("Runtime Environments");
		List<String> parents = new ArrayList<String>();
		parents.add("Server");
		tr.setParents(parents);
		tr.setShellTitle("Preferences");
		
		rules.add(mr);
		rules.add(sr);
		rules.add(tr);
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(rules.size() <= i){
			return false;
		}
		if(i==2 && rule instanceof TreeFilterComplexRule){
			TreeFilterComplexRule tf = (TreeFilterComplexRule)rule;
			return tf.getInitializationRules().get(tf.getInitializationRules().size()-1).equals(this.rules.get(i));
		}
		return this.rules.get(i).equals(rule);
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(this.rules.size() != rules.size()){
			return false;
		}
		for(int i=0;i<rules.size();i++){
			if(i==2 && rules.get(i) instanceof TreeFilterComplexRule){
				TreeFilterComplexRule tf = (TreeFilterComplexRule)rules.get(i);
				TreeRule t = (TreeRule)tf.getInitializationRules().get(tf.getInitializationRules().size()-1);
				if(!t.equals(this.rules.get(i))){
					return false;
				}
			}
			else if(!this.rules.get(i).equals(rules.get(i))){
				return false;
			}
		}
		return true;
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("RuntimePreferencePage() runtimePreferencePage = new RuntimePreferencePage()");
		toReturn.add("runtimePreferencePage.open()");
		return toReturn;
	}
	
	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.wst.server.ui.RuntimePreferencePage";
	}
}
