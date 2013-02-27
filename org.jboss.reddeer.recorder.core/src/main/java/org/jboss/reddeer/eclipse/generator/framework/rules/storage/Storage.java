package org.jboss.reddeer.eclipse.generator.framework.rules.storage;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationRule;

public class Storage {
	
	private List<GenerationRule> rules = new ArrayList<GenerationRule>();

	public List<GenerationRule> getRules() {
		return rules;
	}

	public void setRules(List<GenerationRule> rules) {
		this.rules = rules;
	}

}
