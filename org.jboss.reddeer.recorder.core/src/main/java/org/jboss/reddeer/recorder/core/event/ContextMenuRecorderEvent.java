package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

import org.eclipse.swt.widgets.Widget;

import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ContextMenuRecorderEvent extends RecorderEvent {
	
	//do not call anything else on widget..exception will be thrown
	private Widget widget;
	private String text;
	
	public ContextMenuRecorderEvent(Widget widget,String text){
		this.widget=widget;
		this.text=text;
	}


	public Widget getWidget() {
		return widget;
	}

	public String getText() {
		return WidgetUtils.convert(text);
	}


	@Override
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		return setOfImports;
		
	}
	
}
