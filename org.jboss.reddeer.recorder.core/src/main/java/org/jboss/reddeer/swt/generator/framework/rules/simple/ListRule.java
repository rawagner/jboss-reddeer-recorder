package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.generator.framework.rules.RuleUtils;

public class ListRule extends GenerationSimpleRule{
	
	private String[] selectedItems;
	private int index;
	private String group;
	private String label;
	private String section;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof List && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		selectedItems = ((List)event.widget).getSelection();
		label = WidgetUtils.getLabel(((List)event.widget));
		group = WidgetUtils.getGroup(((List)event.widget));
		index = WidgetUtils.getIndex(((List)event.widget));
		Section sec = WidgetUtils.getSection((List)event.widget);
		if(sec!=null){
			setSection(sec.getText());
		}
		
	}

	@Override
	public java.util.List<String> getActions() {
		java.util.List<String> toReturn = new java.util.ArrayList<String>();
		StringBuilder list = new StringBuilder();
		if(section!=null){
			toReturn.add(RuleUtils.getSectionRule(section));
		}
		list.append("new DefaultList(");
		if(group != null){
			list.append("\""+group+"\",");
		}
		if(label != null){
			list.append("\""+label+"\"");
		} else {
			list.append(index);
		}
		list.append(").select(\""+selectedItems[0]+"\")");
		toReturn.add(list.toString());
		return toReturn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + Arrays.hashCode(selectedItems);
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
		ListRule other = (ListRule) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (index != other.index)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (!Arrays.equals(selectedItems, other.selectedItems))
			return false;
		return true;
	}
	
	@Override
	public java.util.List<String> getImports() {
		java.util.List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.list.DefaultList");
		if(section != null){
			toReturn.add(RuleUtils.SECTION_IMPORT);
		}
		return toReturn;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
	
	
}
