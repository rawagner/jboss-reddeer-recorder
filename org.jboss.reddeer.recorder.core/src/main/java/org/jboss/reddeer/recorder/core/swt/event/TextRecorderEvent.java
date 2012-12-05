package org.jboss.reddeer.recorder.core.swt.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class TextRecorderEvent extends RecorderEvent {

	private String text;
	private String label;
	private int index;
	private String group;
	
	public TextRecorderEvent(String text,String group,String label,int index, String shell, String view){
		this.text=text;
		this.label=label;
		this.index=index;
		this.group=group;
		setShellName(shell);
		setViewName(view);
	}

	//do not convert..written by human
	public String getText() {
		return text;
	}

	@Override
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if (group == null) {
			if (label != null) {
				testBuilder.append("new LabeledText(\"" + getLabel()
							+ "\").setText(\"" + getText() + "\")");
				setOfImports.add(ImportUtils.LABELED_TEXT);
			} else {
				testBuilder.append("new DefaultText(" + index
							+ ").setText(\"" + getText() + "\")");
				setOfImports.add(ImportUtils.DEFAULT_TEXT);
			}
		} else {
				testBuilder.append("new DefaultText(\"" + getGroup() + "\"," + getIndex() + ").setText(\""
							+ getText() + "\")");
				setOfImports.add(ImportUtils.DEFAULT_TEXT);
		}
		return setOfImports;
		
	}

	public String getLabel() {
		return WidgetUtils.convert(label);
	}

	public int getIndex() {
		return index;
	}
	
	public String getGroup(){
		return WidgetUtils.convert(group);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		TextRecorderEvent other = (TextRecorderEvent) obj;
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
		return true;
	}

	

}
