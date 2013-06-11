package org.jboss.reddeer.eclipse.generator.framework.rules;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.framework.adaptor.FilePath;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.generator.framework.AnnotationRule;
import org.eclipse.swtbot.generator.framework.GenerationComplexRule;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.GenerationStackRule;
import org.eclipse.swtbot.generator.framework.Generator;
import org.jboss.reddeer.eclipse.generator.framework.rules.preference.datatools.DriverDefinitionPreferencePageRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.preference.runtime.RuntimePreferencePageRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.browser.BrowserRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.console.ConsoleFocusViewRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.console.ConsoleRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.errorlog.ErrorLogRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.outline.OutlineRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.packageexplorer.PackageExplorerRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.problems.ProblemsRule;
import org.jboss.reddeer.eclipse.generator.framework.rules.view.server.ServersRule;
import org.jboss.reddeer.swt.generator.framework.rules.RedDeerSWTGeneratorRules;

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
		return new RedDeerSWTGeneratorRules().createComplexRules();
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
		stackRules.add(new DriverDefinitionPreferencePageRule());
		stackRules.add(new ServersRule());
		stackRules.add(new BrowserRule());
		return stackRules;
	}

	@Override
	public List<AnnotationRule> createAnnotationRules() {
		return new RedDeerSWTGeneratorRules().createAnnotationRules();
	}

	@Override
	public Image getImage() {
		InputStream is = getClass().getResourceAsStream("/icons/reddeer_logo.png");
		Image image = new Image(Display.getCurrent(), is);
		return image;
	}

}
