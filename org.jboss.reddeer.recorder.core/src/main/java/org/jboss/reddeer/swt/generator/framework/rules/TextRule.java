package org.jboss.reddeer.swt.generator.framework.rules;

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
	private String shellName;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Text && event.type==SWT.Modify;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.setText(((Text)event.widget).getText());
		this.setIndex(WidgetUtils.getIndex((Text)event.widget));
		Shell shell = WidgetUtils.getShell((Text)event.widget);
		if(shell !=null){
			this.setShellName(shell.getText());
		}
		this.setGroup(WidgetUtils.getGroup((Text)event.widget));
		this.setLabel(WidgetUtils.getLabel((Text)event.widget));
		
		
	}

	@Override
	protected String getWidgetAccessor() {
		StringBuilder builder = new StringBuilder();
		if(label != null){
			builder.append("new LabeledText(\""+label+"\"");
			if(group != null){
				builder.append(",\""+group+"\"");
			}
			if(index >0){
				builder.append(","+index);
			}
		} else {
			builder.append("new DefaultText(");
			if(group != null){
				builder.append("\""+group+"\"");
			}
			if(group != null && index >0){
				builder.append(","+index);
			} else if(group == null && index > 0){
				builder.append(index);
			}
		}
		builder.append(")");
		
		return builder.toString();
	}

	@Override
	protected String getActon() {
		return ".setText(\""+text+"\")";
	}

	@Override
	public String getRuleName() {
		return "Text rule";
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

	public String getShellName() {
		return shellName;
	}

	public void setShellName(String shellName) {
		this.shellName = shellName;
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
				+ ((shellName == null) ? 0 : shellName.hashCode());
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
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	

}
