package org.jboss.reddeer.eclipse.generator.framework.rules.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.ui.listener.WorkbenchListener;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TreeFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.CTabWorkbenchRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TreeRule;

public abstract class AbstractOpenViewRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;
	
	public AbstractOpenViewRule(String category, String viewName) {
		rules = new ArrayList<GenerationRule>();
		
		ShellMenuRule mr = new ShellMenuRule();
		List<String> path = new ArrayList<String>();
		path.add("Window");
		path.add("Show View");
		mr.setPath(path);
		mr.setMenu("Other...");
		
		ShellRule sr = new ShellRule();
		sr.setShellTitle("Show View");
		sr.setShellAction(SWT.Activate);
		
		TreeRule tr = new TreeRule();
		tr.setTreeIndex(0);
		tr.setItemText(viewName);
		tr.setShellTitle("Show View");
		List<String> parents = new ArrayList<String>();
		parents.add(category);
		tr.setParents(parents);
		
		ButtonRule br = new ButtonRule();
		br.setGroup(null);
		br.setIndex(1);
		br.setText("OK");
		br.setShellTitle("Show View");
		br.setStyle(SWT.PUSH);
		
		CTabWorkbenchRule ct = new CTabWorkbenchRule();
		ct.setText(viewName);
		ct.setDetail(WorkbenchListener.PART_ACTIVATED);
		
		rules.add(mr);
		rules.add(sr);
		rules.add(tr);
		rules.add(br);
		rules.add(ct);//if part is activated then this event wont be fired
		
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(rules.size() <= i){
			return false;
		}
		if(i == 2 && rule instanceof TreeFilterComplexRule){
				TreeFilterComplexRule tf = (TreeFilterComplexRule)rule;
				return tf.getInitializationRules().get(tf.getInitializationRules().size()-1).equals(this.rules.get(i));
		}
		return rules.get(i).equals(rule);

	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(rules.size() != 4 && rules.size() != 5){ //depends if ctabworkbenchrule was fired
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
}