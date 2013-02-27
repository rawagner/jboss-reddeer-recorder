package org.jboss.reddeer.swt.generator.framework.rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;

public class CTabRule extends GenerationSimpleRule{

	private String text;
	
	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof CTabFolder && event.item instanceof CTabItem && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.text = ((CTabItem)event.item).getText();
		
	}

	@Override
	protected String getWidgetAccessor() {
		return "cTabItem "+text+" activated";
	}

	@Override
	protected String getActon() {
		return "";
	}

	@Override
	public String getRuleName() {
		return "CTab";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CTabRule other = (CTabRule) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
