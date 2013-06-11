package org.jboss.reddeer.eclipse.generator.framework.rules.view.browser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.console.ConsoleClearRule;

public class BrowserRule extends AbstractOpenViewRule{
	
	public BrowserRule(){
		super("General","Internal Web Browser");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new ConsoleClearRule());
		return methods;
	}
	
	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("BrowserView browserView = new BrowserView()");
		toReturn.add("browserView.open()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.browser.BrowserView";
	}
} 