package org.jboss.reddeer.eclipse.generator.framework.rules.view.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.AbstractOpenViewRule;

public class PackageExplorerRule extends AbstractOpenViewRule{
	
	public PackageExplorerRule(){
		super("Java","Package Explorer");
	}

	@Override
	public List<GenerationStackRule> getMethods() {
		List<GenerationStackRule> methods = new ArrayList<GenerationStackRule>();
		methods.add(new PackageExplorerSelectProject());
		methods.add(new PackageExplorerDeleteProjectRule());
		return methods;
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("PackageExplorer packageExporer = new PackageExplorer()");
		toReturn.add("packageExplorer.open()");
		return toReturn;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.eclipse.ui.packageexplorer.PackageExplorer";
	}
	
	
	

}
