package org.jboss.reddeer.recorder.core.wizard;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.jboss.reddeer.recorder.core.action.RecordingState;
import org.jboss.reddeer.recorder.core.swt.listener.ListenerController;

public class RecordTestWizard extends Wizard{

	protected RecordTestWizardPage pageOne;
	private Display display;
	private IWorkbenchPage page;
	private ListenerController controller;
	private RecordingState recordingStateService;

	protected WizardNewFileCreationPage pageTwo;
	
	public RecordTestWizard(Display display,ListenerController controller,IWorkbenchPage page,RecordingState recordingStateService){
		super();
		this.recordingStateService=recordingStateService;
		this.page=page;
		this.display=display;
		this.controller=controller;
		setNeedsProgressMonitor(false);
	}
	
	@Override
	public void addPages() {
	    pageOne = new RecordTestWizardPage(recordingStateService);
	    pageTwo = new WizardNewFileCreationPage("abc", null);
	    addPage(pageOne);
	    //addPage(pageTwo);
	}
	
	@Override
	public boolean performFinish() {
		controller.hookupWorkbenchListeners(page);
		controller.hookupSWTEventsListeners(display);
		return true;
	}

}
