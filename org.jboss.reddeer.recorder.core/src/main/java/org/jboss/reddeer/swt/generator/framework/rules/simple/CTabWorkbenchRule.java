package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.ui.listener.WorkbenchListener;

public class CTabWorkbenchRule extends GenerationSimpleRule{

	private String text;
	private int detail;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget == null && event.data instanceof String && event.type==SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.text = (String)event.data;
		this.detail=event.detail;
		
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append("new WorkbenchView(\""+text+"\")");
		if(detail==WorkbenchListener.PART_CLOSED){
			builder.append(".close()");
		}
		toReturn.add(builder.toString());
		return toReturn;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + detail;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CTabWorkbenchRule other = (CTabWorkbenchRule) obj;
		if (detail != other.detail)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public int getDetail() {
		return detail;
	}

	public void setDetail(int detail) {
		this.detail = detail;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.workbench.view.impl.WorkbenchView";
	}

}
