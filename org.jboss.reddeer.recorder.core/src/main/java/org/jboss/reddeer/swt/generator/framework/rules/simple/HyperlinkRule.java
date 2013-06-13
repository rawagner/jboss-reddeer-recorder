package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.generator.framework.rules.RuleUtils;

public class HyperlinkRule extends GenerationSimpleRule{
	
	private String text;
	private int index;
	private String section;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Hyperlink && event.type == SWT.MouseDown;
	}

	@Override
	public void initializeForEvent(Event event) {
		setText(((Hyperlink)event.widget).getText());
		setIndex(WidgetUtils.getIndex((Hyperlink)event.widget));
		Section s = WidgetUtils.getSection((Hyperlink)event.widget);
		if(s!=null){
			setSection(s.getText());
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		if(section != null){
			toReturn.add(RuleUtils.getSectionRule(section));
		}
		toReturn.add("hyperlink "+text);
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
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("hyperlink_Import");
		if(section != null){
			toReturn.add(RuleUtils.SECTION_IMPORT);
		}
		return toReturn;
	}

}
