package org.eclipse.swtbot.generator.framework;

import java.util.List;

public abstract class GenerationStackRule extends GenerationRule{
	
	private List<GenerationRule> initializationRules;

	/**
	 * Checks whether given GenerationSimpleRule applies to complex rule
	 * @param rule to match with complex rule
	 * @param i which rule from complex rule to match
	 * @return true if given SimpleRule applies to i-indexed rule
	 */
	public abstract boolean appliesToPartially(GenerationRule rule, int i);
	
	/**
	 * Checks whether given list of GenerationSimpleRule-s applies to complex rule
	 * @param rules to match with complex rule
	 * @return true if given list of SimpleRule applies complex rule
	 */
	public abstract boolean appliesTo(List<GenerationRule> rules);
	
	
	/**
	 * Initializes complex rule for given simple rules
	 * @param rules to initialize complex rule for
	 */
	public void initializeForRules(List<GenerationRule> rules){
		this.initializationRules=rules;
	}
	
	/**
	 * 
	 * @return rules which this complex rule was initialized for
	 */
	public List<GenerationRule> getInitializationRules(){
		return initializationRules;
	}
	
	public abstract List<GenerationStackRule> getMethods();

}
