package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

import org.eclipse.swt.SWT;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ViewToolBarRecorderEvent extends RecorderEvent{
	
	private int style;
	private String tooltypText;
	
	public ViewToolBarRecorderEvent(String tooltypText,int style, String shell, String view){
		this.tooltypText=tooltypText;
		this.style=style;
		setShellName(shell);
		setViewName(view);
	}
	
	public int getStyle() {
		return style;
	}
	public String getTooltypText() {
		return WidgetUtils.convert(tooltypText);
	}
	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if ((style & SWT.PUSH) != 0) {
			testBuilder.append("new ViewToolItem(\""
					+ getTooltypText() + "\")");
			setOfImports.add(ImportUtils.VIEW_TOOLBAR_ITEM);
		} else if ((style & SWT.DROP_DOWN) != 0) {
			testBuilder.append("bot.toolbarDropDownButtonWithTooltip(\""
					+ getTooltypText() + "\")");
		}
		return setOfImports;

	}

}
