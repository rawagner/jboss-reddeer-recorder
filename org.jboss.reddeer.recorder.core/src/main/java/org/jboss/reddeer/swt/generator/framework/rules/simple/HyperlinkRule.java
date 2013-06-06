package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.Section;

public class HyperlinkRule extends GenerationSimpleRule{
	
	private String text;
	private int index;
	private String section;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof ImageHyperlink && event.type == SWT.MouseDown;
	}

	@Override
	public void initializeForEvent(Event event) {
		setText(((ImageHyperlink)event.widget).getText());
		setIndex(WidgetUtils.getIndex((ImageHyperlink)event.widget));
		Section s = WidgetUtils.getSection((ImageHyperlink)event.widget);
		if(s!=null){
			setSection(s.getText());
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("hyperlink "+text+" in section "+section);
		return toReturn;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public String getImport() {
		// TODO Auto-generated method stub
		return null;
	}

}
