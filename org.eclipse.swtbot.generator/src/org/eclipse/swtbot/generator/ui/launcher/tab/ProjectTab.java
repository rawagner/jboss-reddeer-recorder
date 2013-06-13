package org.eclipse.swtbot.generator.ui.launcher.tab;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.pde.ui.launcher.AbstractLauncherTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swtbot.generator.ui.launcher.GeneratorLauncherConfiguration;

public class ProjectTab extends AbstractLauncherTab{
	
	private Composite composite;
	
	

	public void createControl(Composite parent) {
		final ScrolledComposite scrollContainer = new ScrolledComposite(parent, SWT.V_SCROLL);
		scrollContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		composite = new Composite(scrollContainer, SWT.NONE);
		scrollContainer.setContent(composite);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button projectNew = new Button(composite, SWT.RADIO);
		projectNew.setText("Run with current Eclipse instance");
		projectNew.setSelection(true);
		final Button projectExisting = new Button(composite, SWT.RADIO);
		projectExisting.setText("Run with new Eclipse instance");
		
		Dialog.applyDialogFont(composite);
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		scrollContainer.setExpandHorizontal(true);
		setControl(scrollContainer);
		
	}

	public String getName() {
		return "Project";
	}

	public void initializeFrom(ILaunchConfiguration arg0) {
		// TODO Auto-generated method stub
		
	}

	public void performApply(ILaunchConfigurationWorkingCopy arg0) {
		Control[] controls = composite.getChildren();
		if(((Button)controls[0]).getSelection()){
			arg0.setAttribute(GeneratorLauncherConfiguration.RUN_IN_NEW_INSTANCE, true);
		} else if(((Button)controls[1]).getSelection()){
			arg0.setAttribute(GeneratorLauncherConfiguration.RUN_IN_NEW_INSTANCE, false);
		}
	}
	
	

	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
	}

	@Override
	public void validateTab() {
		
	}

}
