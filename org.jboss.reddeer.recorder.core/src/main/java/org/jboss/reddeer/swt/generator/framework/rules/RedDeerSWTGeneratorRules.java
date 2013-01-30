package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;

public class RedDeerSWTGeneratorRules extends Generator{

	public List<GenerationSimpleRule> createSimpleRules() {
		List<GenerationSimpleRule> res = new ArrayList<GenerationSimpleRule>();
		res.add(new ButtonRule());
		res.add(new ShellMenuRule());
		res.add(new TreeRule());
		res.add(new ToolBarRule());
		res.add(new TextRule());
		res.add(new ComboRule());
		res.add(new TabRule());
		res.add(new TableRule());
		res.add(new ShellRule());
		res.add(new ContextMenuRule());
		res.add(new CTabRule());
		return res;
	}

	public String getLabel() {
		return "RedDeer SWT";
	}

	public List<GenerationStackRule> createStackRules() {
		return null;
	}

	public boolean useStacks() {
		return false;
	}

	@Override
	protected List<GenerationComplexRule> sortComplexRules() {
		List<GenerationComplexRule> complexRules = new ArrayList<GenerationComplexRule>();
		complexRules.add(new ToolBarMenuComplexRule());
		
		Collections.sort(complexRules, new Comparator<GenerationComplexRule>() {
			public int compare(GenerationComplexRule g1, GenerationComplexRule g2) {
				return g2.getInitializationRules().size()-g1.getInitializationRules().size();
			}
		});
		return complexRules;
	}

	@Override
	protected List<GenerationStackRule> sortStackRules() {
		return null;
	}

}
