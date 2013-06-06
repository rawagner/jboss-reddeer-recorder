package org.eclipse.swtbot.generator.ui.launcher.tab;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.pde.ui.launcher.AbstractLauncherTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ProjectTab extends AbstractLauncherTab{

	public void createControl(Composite parent) {
		final ScrolledComposite scrollContainer = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrollContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Composite composite = new Composite(scrollContainer, SWT.NONE);
		scrollContainer.setContent(composite);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		final Button projectNew = new Button(composite, SWT.RADIO);
		projectNew.setText("Create new project");
		final Button projectExisting = new Button(composite, SWT.RADIO);
		projectExisting.setText("Choose existing project");
		final Button noProject = new Button(composite, SWT.RADIO);
		noProject.setText("Just generate code");
		
	}

	public String getName() {
		return "Project";
	}

	public void initializeFrom(ILaunchConfiguration arg0) {
		// TODO Auto-generated method stub
		
	}

	public void performApply(ILaunchConfigurationWorkingCopy arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateTab() {
		// TODO Auto-generated method stub
		
	}

}
