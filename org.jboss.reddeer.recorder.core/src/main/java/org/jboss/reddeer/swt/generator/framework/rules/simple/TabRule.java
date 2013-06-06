package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;

public class TabRule extends GenerationSimpleRule{
	
	private String text;
	
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof TabFolder && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.text = ((TabFolder)event.widget).getSelection()[0].getText();
		
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("new DefaultTabItem(\"" + text + "\").activate()");
		return toReturn;
		
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
		TabRule other = (TabRule) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String getImport() {
		return "org.jboss.reddeer.swt.impl.tab.DefaultTabItem";
	}

}
