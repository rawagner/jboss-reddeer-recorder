package org.eclipse.swtbot.generator.ui.launcher;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.pde.core.plugin.TargetPlatform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.pde.launching.IPDELauncherConstants;


public class GeneratorLaunchHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public GeneratorLaunchHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event){
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType("org.eclipse.swtbot.generator.ui.launcher.GeneratorLauncherConfiguration");
		ILaunchConfigurationWorkingCopy wc = null;
		ILaunchConfiguration c=null;
		
		try {
			wc = type.newInstance(null, "botgenerator");
			wc.setAttribute(IPDELauncherConstants.USE_PRODUCT, true); //we want to use a product, not application
			wc.setAttribute(IPDELauncherConstants.PRODUCT, TargetPlatform.getDefaultProduct()); //get the default product
			c=wc.doSave();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		DebugUITools.openLaunchConfigurationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),c,"org.eclipse.debug.ui.launchGroup.run", null);

		return null;
	}
}
