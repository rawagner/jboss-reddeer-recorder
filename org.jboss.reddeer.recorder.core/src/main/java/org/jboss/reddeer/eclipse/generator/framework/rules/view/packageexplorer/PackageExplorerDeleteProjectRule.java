package org.jboss.reddeer.eclipse.generator.framework.rules.view.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swtbot.generator.framework.GenerationRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.CheckBoxFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ContextMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;

public class PackageExplorerDeleteProjectRule extends GenerationStackRule{
	
	private List<GenerationRule> rules;
	private boolean withoutCheckRule;
	
	public PackageExplorerDeleteProjectRule(){
		rules = new ArrayList<GenerationRule>();
		
		ContextMenuRule cm = new ContextMenuRule();
		cm.setMenu("Delete");
		cm.setPath(new ArrayList<String>());
		
		ShellRule sr = new ShellRule();
		sr.setShellTitle("Delete Resources");
		sr.setShellAction(SWT.Activate);
		
		ButtonRule br = new ButtonRule();
		br.setShellTitle("Delete Resources");
		br.setText("Delete project contents on disk (cannot be undone)");
		br.setIndex(0);
		br.setStyle(SWT.CHECK);
		
		ButtonRule br1 = new ButtonRule();
		br1.setShellTitle("Delete Resources");
		br1.setText("OK");
		br1.setIndex(2);
		br1.setStyle(SWT.PUSH);
		
		rules.add(cm);
		rules.add(sr);
		rules.add(br);
		rules.add(br1);
		
	}

	@Override
	public boolean appliesToPartially(GenerationRule rule, int i) {
		if(i>=this.rules.size()){
			return false;
		}
		if(i==2 && rule instanceof CheckBoxFilterComplexRule){
			CheckBoxFilterComplexRule cf = (CheckBoxFilterComplexRule)rule;
			return cf.getInitializationRules().get(cf.getInitializationRules().size()-1).equals(this.rules.get(i));
		}
		if(i==2 && rule instanceof ButtonRule){
			if(this.rules.get(i+1).equals(rule)){
				withoutCheckRule=true;
				return true;
			}
			return false;
		}
		if(withoutCheckRule && i>2){
			if(i+1 >= this.rules.size()){
				return false;
			}
			return this.rules.get(i+1).equals(rule);
		}
		return this.rules.get(i).equals(rule);
	}

	@Override
	public boolean appliesTo(List<GenerationRule> rules) {
		if(!withoutCheckRule){
			if(rules.size() != this.rules.size()){
				return false;
			}
			for(int i=0;i<rules.size();i++){
				if(i==2 && rules.get(i) instanceof CheckBoxFilterComplexRule){
					CheckBoxFilterComplexRule cf = (CheckBoxFilterComplexRule)rules.get(i);
					if(!cf.getInitializationRules().get(cf.getInitializationRules().size()-1).equals(this.rules.get(i))){
						return false;
					}
				}
				else if(!this.rules.get(i).equals(rules.get(i))){
					return false;
				}
			}
			return true;
		} else {
			if(rules.size() != this.rules.size()-1){
				return false;
			}
			int i=0;
			while(i<this.rules.size()-1){
				if(i>=2){
					if(!this.rules.get(i+1).equals(rules.get(i))){
						return false;
					}
				} else {
					if(!this.rules.get(i).equals(rules.get(i))){
						return false;
					}
				}
				i++;
			}
			return true;
		}
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		return null;
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		boolean deleteFromDisc=false;
		if(withoutCheckRule){
			deleteFromDisc=false;
		} else {
			CheckBoxFilterComplexRule cr = ((CheckBoxFilterComplexRule)getInitializationRules().get(2));
			deleteFromDisc = ((ButtonRule)cr.getInitializationRules().get(cr.getInitializationRules().size()-1)).getToggle();
		}
		toReturn.add("project.delete("+deleteFromDisc+")");
		return toReturn;
	}

	@Override
	public List<String> getImports() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
