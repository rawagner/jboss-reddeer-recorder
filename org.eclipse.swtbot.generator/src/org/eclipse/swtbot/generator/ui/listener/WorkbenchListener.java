package org.eclipse.swtbot.generator.ui.listener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.ui.BotGeneratorEventDispatcher;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

public class WorkbenchListener implements IPartListener2{
	
	private BotGeneratorEventDispatcher dispatcher;
	public static final int PART_ACTIVATED=1;
	public static final int PART_CLOSED=0;
	
	public WorkbenchListener(BotGeneratorEventDispatcher dispatcher){
		this.dispatcher=dispatcher;
	}

	public void partActivated(IWorkbenchPartReference arg0) {
		System.out.println("activated "+arg0.getPartName());
		Event e = new Event();
		e.type=SWT.Selection;
		e.data=new String(arg0.getPartName());
		e.detail=PART_ACTIVATED;
		dispatcher.handleEvent(e);
	}

	public void partBroughtToTop(IWorkbenchPartReference arg0) {
		
	}

	public void partClosed(IWorkbenchPartReference arg0) {
		System.out.println("closed "+arg0.getPartName());
		Event e = new Event();
		e.type=SWT.Selection;
		e.data=new String(arg0.getPartName());
		e.detail=PART_CLOSED;
		dispatcher.handleEvent(e);
		
	}

	public void partDeactivated(IWorkbenchPartReference arg0) {
		System.out.println("deactivated "+arg0.getPartName());
		
	}

	public void partHidden(IWorkbenchPartReference arg0) {
		System.out.println("hidden "+arg0.getPartName());
		
	}

	public void partInputChanged(IWorkbenchPartReference arg0) {

		
	}

	public void partOpened(IWorkbenchPartReference arg0) {
		System.out.println("opened "+arg0.getPartName());
	}

	public void partVisible(IWorkbenchPartReference arg0) {
		System.out.println("visible "+arg0.getPartName());
	}

}
