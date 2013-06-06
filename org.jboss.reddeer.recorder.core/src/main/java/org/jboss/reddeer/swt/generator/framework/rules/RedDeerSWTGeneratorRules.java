package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.jboss.reddeer.swt.generator.framework.rules.complex.CheckBoxFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.ComboComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TextComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.ToolBarMenuComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.complex.TreeFilterComplexRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ButtonRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.CTabWorkbenchRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ComboRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ContextMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.HyperlinkRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ListRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellMenuRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ShellRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TabRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TableRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TextRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.ToolBarRule;
import org.jboss.reddeer.swt.generator.framework.rules.simple.TreeRule;

public class RedDeerSWTGeneratorRules implements Generator{

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
		res.add(new CTabWorkbenchRule());
		res.add(new ListRule());
		res.add(new HyperlinkRule());
		return res;
	}

	public String getLabel() {
		return "RedDeer SWT";
	}

	@Override
	public List<GenerationComplexRule> createComplexRules() {
		List<GenerationComplexRule> res = new ArrayList<GenerationComplexRule>();
		res.add(new ToolBarMenuComplexRule());
		res.add(new ComboComplexRule());
		res.add(new TextComplexRule());
		res.add(new TreeFilterComplexRule());
		res.add(new CheckBoxFilterComplexRule());
		return res;
	}

	@Override
	public List<GenerationStackRule> createStackRules() {
		// TODO Auto-generated method stub
		return null;
	}
}
