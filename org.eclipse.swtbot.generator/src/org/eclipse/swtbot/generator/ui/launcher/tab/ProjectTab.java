package org.eclipse.swtbot.generator.ui.launcher.tab;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.pde.ui.launcher.AbstractLauncherTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

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
		projectNew.setText("Create new project from reddeer archetype");
		final Button projectExisting = new Button(composite, SWT.RADIO);
		projectExisting.setText("Choose existing project");
		final Combo projectsCombo = new Combo(composite, SWT.DROP_DOWN);
		
		for(IProject project: ResourcesPlugin.getWorkspace().getRoot().getProjects()){
			try {
				if(project.isOpen() && project.isNatureEnabled(JavaCore.NATURE_ID)){
					projectsCombo.add(project.getName());
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		projectsCombo.setEnabled(false);
		
		projectExisting.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(projectExisting.getSelection()){
					projectsCombo.setEnabled(true);
				} else{
					projectsCombo.setEnabled(false);
				}
			}
			
		});
		
		final Button noProject = new Button(composite, SWT.RADIO);
		noProject.setText("Just generate code");
		
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
			System.out.println("generate archetype");
		} else if(((Button)controls[1]).getSelection()){
			System.out.println("choosed project "+((Combo)controls[2]).getItem(((Combo)controls[2]).getSelectionIndex()));
		}
	}
	
	

	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateTab() {
		// TODO Auto-generated method stub
		
	}

}
