package org.eclipse.swtbot.generator.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.pde.launching.EclipseApplicationLaunchConfiguration;
import org.eclipse.swtbot.generator.ui.StartupRecorder;

public class GeneratorLauncherConfiguration extends EclipseApplicationLaunchConfiguration{	
	
	public static String RUN_IN_NEW_INSTANCE = "org.eclipse.swtbot.generator.instance";
	
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException{
		if(configuration.getAttribute(RUN_IN_NEW_INSTANCE, false)){
			System.out.println("xxxxxxx");
			System.setProperty(StartupRecorder.ENABLEMENT_PROPERTY, "true");
			StartupRecorder sr = new StartupRecorder();
			sr.earlyStartup();
		} else {
			ILaunchConfigurationWorkingCopy c=configuration.getWorkingCopy();
			c.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,"-D"+StartupRecorder.ENABLEMENT_PROPERTY+"=true");
			configuration=c.doSave();
			super.launch(configuration, mode, launch, monitor);
		}
		
		
	}
}
