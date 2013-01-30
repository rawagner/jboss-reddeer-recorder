package org.jboss.reddeer.recorder.core.action;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;
import org.jboss.reddeer.recorder.core.eclipse.generator.GenerateTestEclipse;
import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.jboss.reddeer.recorder.core.swt.generator.GenerateTestSWT;
import org.jboss.reddeer.recorder.core.swt.listener.ListenerController;
import org.osgi.framework.Bundle;

public class StopRecordTest extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISourceProviderService sourceProviderService = (ISourceProviderService) HandlerUtil
		       .getActiveWorkbenchWindow(event).getService(ISourceProviderService.class);
		
		RecordingState recordingStateService = (RecordingState) sourceProviderService
		        .getSourceProvider(RecordingState.MY_STATE);
		    recordingStateService.toggleRecording();
		    for(IBundleGroupProvider prov: Platform.getBundleGroupProviders()){
				for(IBundleGroup gp : prov.getBundleGroups()){
					//System.out.println("FEATURE "+gp.getIdentifier());
					for(Bundle b:gp.getBundles()){
						if(b.getState() == b.ACTIVE){
							System.out.println("PLUGIN "+b.getSymbolicName());
						}
					}
				}
			}
		    
		    ListenerController controller = RecordTest.getListnenerController();
		    IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		    controller.removeSWTEventsListeners(Display.getCurrent(), page);
		    generate(controller.getEvents(),recordingStateService.getTestName());
		return null;
	}
	
	public void generate(List<RecorderEvent> events,String testName){
		GenerateTestSWT generateTest = new GenerateTestSWT();
		generateTest.generate(events,testName);
		
		//GenerateTestEclipse gen = new GenerateTestEclipse();
		//gen.generate(events, testName);
	}

}
