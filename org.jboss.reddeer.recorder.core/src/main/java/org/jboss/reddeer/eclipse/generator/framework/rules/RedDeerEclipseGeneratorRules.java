package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.jboss.reddeer.eclipse.generator.framework.rules.console.ConsoleRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.errorlog.ErrorLogRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.outline.OutlineRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.packageexplorer.PackageExplorerRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.problems.ProblemsRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.runtime.RuntimePreferencePageRule;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerSWTGeneratorRules;

public class RedDeerEclipseGeneratorRules extends Generator {

	public List<GenerationSimpleRule> createSimpleRules() {
		return new RedDeerSWTGeneratorRules().createSimpleRules();
	}

	public String getLabel() {
		return "RedDeer Eclipse";
	}

	public boolean useStacks() {
		return true;
	}

	@Override
	protected List<GenerationComplexRule> sortComplexRules() {
		return new RedDeerSWTGeneratorRules().createComplexRules();
	}

	@Override
	protected List<GenerationStackRule> sortStackRules() {
		List<GenerationStackRule> stackRules = new ArrayList<GenerationStackRule>();
		stackRules.add(new ErrorLogRule());
		stackRules.add(new NewWizardRule());
		stackRules.add(new TestRule());
		stackRules.add(new PackageExplorerRule());
		stackRules.add(new ConsoleRule());
		stackRules.add(new ProblemsRule());
		stackRules.add(new OutlineRule());
		stackRules.add(new RuntimePreferencePageRule());

		Collections.sort(stackRules, new Comparator<GenerationStackRule>() {
			
			public int compare(GenerationStackRule g1, GenerationStackRule g2) {
				return g2.getInitializationRules().size()-g1.getInitializationRules().size();
			}
		
		});
		return stackRules;
	}

}
