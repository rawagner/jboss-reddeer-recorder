package org.eclipse.swtbot.generator.ui.launcher;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.pde.launching.EclipseApplicationLaunchConfiguration;

public class GeneratorLauncherConfiguration extends EclipseApplicationLaunchConfiguration{	
	
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException{
		ILaunchConfigurationWorkingCopy c=null;
		c=configuration.getWorkingCopy();
		c.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS,"-Dorg.eclipse.swtbot.generator.enable=true");
		configuration=c.doSave();
		/*
		IProgressMonitor progressMonitor = new NullProgressMonitor();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("DesiredProjectName");
		project.setDe
		project.create(progressMonitor);
		project.open(progressMonitor);
		*/
		super.launch(configuration, mode, launch, monitor);
		
	}
}
