package org.jboss.reddeer.eclipse.generator.framework.rules.view.outline;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class OutlineRule extends AbstractOpenViewRule{
	
	
	public OutlineRule(){
		super("General","Outline");
	}
	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new OutlineCollapseAllRule());
		return methods;
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("OutlineView outlineView = new OutlineView()");
		toReturn.add("outlineView.open()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.views.contentoutline.OutlineView";
	}
	
	


}
