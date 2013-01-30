package org.jboss.reddeer.recorder.core.swt.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPage;

import org.jboss.reddeer.recorder.core.swt.event.RecorderEvent;
import org.osgi.framework.Bundle;

public class ListenerController {

	private List<RecorderEvent> events = new ArrayList<RecorderEvent>();
	private List<MenuItem> menus = new ArrayList<MenuItem>();

	private WorkbenchListener workbenchListener;
	private KeyDownListener keyDownListener;
	private ContextMenuListener contextMenuListnener;
	private MenuListener menuListener;
	private ShellListener shellListener;
	private SelectionListener selectionListener;
	private DefaultSelectionListener defaultSelectionListener;
	private ModifyListener modifyListener;
	private HyperlinkListener hyperlinkListener;
	private CloseListener closeListener;
	

	public ListenerController() {
		workbenchListener = new WorkbenchListener(events, menus);
		keyDownListener = new KeyDownListener(events, menus);
		contextMenuListnener = new ContextMenuListener(events, menus);
		menuListener = new MenuListener(events, menus);
		shellListener = new ShellListener(events, menus);
		selectionListener = new SelectionListener(events, menus);
		defaultSelectionListener = new DefaultSelectionListener(events, menus);
		modifyListener = new ModifyListener(events, menus);
		hyperlinkListener = new HyperlinkListener(events, menus);
		closeListener = new CloseListener(events, menus);
	}

	public List<RecorderEvent> getEvents() {
		return events;
	}

	public void removeSWTEventsListeners(Display display, IWorkbenchPage page) {
		page.removePartListener(workbenchListener);
		display.removeFilter(SWT.KeyDown, keyDownListener);
		display.removeFilter(SWT.MenuDetect, contextMenuListnener);
		display.removeFilter(SWT.Arm, menuListener);
		display.removeFilter(SWT.Show, shellListener);
		display.removeFilter(SWT.Selection, selectionListener);
		display.removeFilter(SWT.DefaultSelection, defaultSelectionListener);
		display.removeFilter(SWT.Modify, modifyListener);
		display.removeFilter(SWT.MouseDown, hyperlinkListener);
		display.removeFilter(SWT.Close, closeListener);

	}

	public void hookupWorkbenchListeners(IWorkbenchPage page) {
		page.addPartListener(workbenchListener);
	}

	public void hookupJobListeners() {
		IJobManager manager = Job.getJobManager();
		manager.addJobChangeListener(new JobListener());
	}

	public void hookupSWTEventsListeners(Display display) {
		// display.addFilter(SWT.MouseDoubleClick, new
		// DoubleClickListener(events, menus));
		display.addFilter(SWT.KeyDown, keyDownListener);
		display.addFilter(SWT.MenuDetect, contextMenuListnener);
		// menu
		display.addFilter(SWT.Arm, menuListener);
		// new shell
		display.addFilter(SWT.Show, shellListener);
		display.addFilter(SWT.Selection, selectionListener);
		display.addFilter(SWT.DefaultSelection, defaultSelectionListener);
		display.addFilter(SWT.Modify, modifyListener);
		display.addFilter(SWT.MouseDown, hyperlinkListener);
		display.addFilter(SWT.Close, closeListener);
	}

}
