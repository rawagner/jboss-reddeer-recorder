package org.jboss.reddeer.recorder.core.action;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;
import org.jboss.reddeer.recorder.core.swt.listener.ListenerController;
import org.jboss.reddeer.recorder.core.wizard.RecordTestWizard;

public class RecordTest extends AbstractHandler {

	private Display display;
	private static ListenerController controller;

	public static ListenerController getListnenerController() {
		return controller;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISourceProviderService sourceProviderService = (ISourceProviderService) HandlerUtil
				.getActiveWorkbenchWindow(event).getService(ISourceProviderService.class);
		RecordingState recordingStateService = (RecordingState) sourceProviderService
				.getSourceProvider(RecordingState.MY_STATE);
		recordingStateService.toggleRecording();
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		controller = new ListenerController();
		display = Display.getCurrent();

		
		
		WizardDialog wizardDialog = new WizardDialog(display.getActiveShell(),
				new RecordTestWizard(display,controller,page,recordingStateService));
		wizardDialog.open();
		// controller.hookupJobListeners();
		//controller.hookupWorkbenchListeners(page);
		//controller.hookupSWTEventsListeners(display);
		return null;
	}

}
