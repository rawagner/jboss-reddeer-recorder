package org.eclipse.swtbot.generator.ui.launcher;

import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.pde.ui.launcher.ConfigurationTab;
import org.eclipse.pde.ui.launcher.EclipseLauncherTabGroup;
import org.eclipse.pde.ui.launcher.MainTab;
import org.eclipse.pde.ui.launcher.PluginsTab;
import org.eclipse.pde.ui.launcher.TracingTab;
import org.eclipse.swtbot.generator.ui.launcher.tab.ProjectTab;

public class GeneratorTabGroup extends EclipseLauncherTabGroup{
	
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		//super.createTabs(dialog, mode);
		
		ILaunchConfigurationTab[] tabs = null;
		tabs = new ILaunchConfigurationTab[] {new ProjectTab(), new MainTab(), new JavaArgumentsTab(), new PluginsTab(), new ConfigurationTab(), new TracingTab(), new EnvironmentTab(), new CommonTab()};
		setTabs(tabs);
	}

}
