package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.jboss.reddeer.eclipse.generator.framework.rules.runtime.RuntimePreferencePageRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.console.ConsoleFocusViewRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.console.ConsoleRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.errorlog.ErrorLogRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.outline.OutlineRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.packageexplorer.PackageExplorerRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.problems.ProblemsRule;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerSWTGeneratorRules;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;

public class RedDeerEclipseGeneratorRules implements Generator {

	public List<GenerationSimpleRule> createSimpleRules() {
		List<GenerationSimpleRule> sRules = new RedDeerSWTGeneratorRules().createSimpleRules();
		return sRules;
	}

	public String getLabel() {
		return "RedDeer Eclipse";
	}

	public boolean useStacks() {
		return true;
	}

	@Override
	public List<GenerationComplexRule> createComplexRules() {
		List<GenerationComplexRule> cRules = new RedDeerSWTGeneratorRules().createComplexRules();
		return cRules;
	}
	
	public List<GenerationStackRule> createStackRules(){
		List<GenerationStackRule> stackRules = new ArrayList<GenerationStackRule>();
		stackRules.add(new ConsoleRule());
		stackRules.add(new ErrorLogRule());
		stackRules.add(new OutlineRule());
		stackRules.add(new PackageExplorerRule());
		stackRules.add(new ProblemsRule());
		stackRules.add(new RuntimePreferencePageRule());
		stackRules.add(new ConsoleFocusViewRule());
		return stackRules;
	}

}
