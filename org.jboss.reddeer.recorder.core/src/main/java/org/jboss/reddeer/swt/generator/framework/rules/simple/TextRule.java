package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class TextRule extends GenerationSimpleRule{
	
	private String text;
	private String group;
	private int index;
	private String label;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Text && event.type==SWT.Modify && !((Text)event.widget).getText().equals("") &&
				!((Text)event.widget).getMessage().equals(((Text)event.widget).getText());
	}

	@Override
	public void initializeForEvent(Event event) {
		this.setText(((Text)event.widget).getText());
		this.setIndex(WidgetUtils.getIndex((Text)event.widget));
		Shell s = WidgetUtils.getShell((Text)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		this.setGroup(WidgetUtils.getGroup((Text)event.widget));
		this.setLabel(WidgetUtils.getLabel((Text)event.widget));
		
		
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if(label != null){
			builder.append("new LabeledText(\""+label+"\"");
			if(group != null){
				builder.append(",\""+group+"\"");
			}
		} else {
			builder.append("new DefaultText(");
			if(group != null){
				builder.append("\""+group+"\"");
				if(index >0){
					builder.append(","+index);
				} 
			} else if(group == null){
				builder.append(index);
			}
		}
		builder.append(").setText(\""+text+"\")");
		toReturn.add(builder.toString());
		return toReturn;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
				+ ((getShellTitle() == null) ? 0 : getShellTitle().hashCode());
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
		TextRule other = (TextRule) obj;
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
		if (getShellTitle() == null) {
			if (other.getShellTitle() != null)
				return false;
		} else if (!getShellTitle().equals(other.getShellTitle()))
			return false;
		return true;
	}

	@Override
	public String getImport() {
		if(label!=null){
			return "org.jboss.reddeer.swt.impl.text.LabeledText";
		} return "org.jboss.reddeer.swt.impl.text.DefaultText";
	}
	
	

}
