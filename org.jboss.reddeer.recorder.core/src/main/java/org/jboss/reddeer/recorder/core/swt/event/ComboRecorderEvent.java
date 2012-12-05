package org.jboss.reddeer.recorder.core.swt.event;

import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ComboRecorderEvent extends RecorderEvent {

	private String text;
	private String label;
	private int index;
	private int selection;
	private String group;

	public ComboRecorderEvent(String text, String label, String group,
			int index, int selection, String shell, String view) {
		this.text = text;
		this.label = label;
		this.index = index;
		this.selection = selection;
		this.group = group;
		setShellName(shell);
		setViewName(view);
	}

	// do not convert..written by human
	public String getText() {
		return text;
	}

	public String getLabel() {
		return WidgetUtils.convert(label);
	}

	public int getIndex() {
		return index;
	}

	public int getSelection() {
		return selection;
	}

	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if (group == null) {
			if (label != null) {
				if (text != null) {
					testBuilder.append("new DefaultCombo(\"" + getLabel()
							+ "\").setText(\"" + getText() + "\")");
				} else {
					testBuilder.append("new DefaultCombo(\"" + getLabel()
							+ "\").setSelection(" + selection + ")");
				}
			} else {
				if (text != null) {
					testBuilder.append("new DefaultCombo(" + index
							+ ").setText(\"" + getText() + "\")");
				} else {
					testBuilder.append("new DefaultCombo(" + index
							+ ").setSelection(" + selection + ")");
				}
			}
		} else {
			if (label != null) {
				if (text != null) {
					testBuilder.append("new DefaultCombo(\"" + getGroup()
							+ "\",\"" + getLabel() + "\").setText(\""
							+ getText() + "\")");
				} else {
					testBuilder.append("new DefaultCombo(\"" + getGroup()
							+ "\",\"" + getLabel() + "\").setSelection("
							+ selection + ")");
				}
			} else {
				if (text != null) {
					testBuilder.append("new DefaultCombo(\"" + getGroup()
							+ "\"," + index + ").setText(\"" + getText()
							+ "\")");
				} else {
					testBuilder.append("new DefaultCombo(\"" + getGroup()
							+ "\"," + index + ").setSelection(" + selection
							+ ")");
				}
			}
		}
		setOfImports.add(ImportUtils.DEFAULT_COMBO);
		return setOfImports;
	}

	public String getGroup() {
		return WidgetUtils.convert(group);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + selection;
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
		ComboRecorderEvent other = (ComboRecorderEvent) obj;
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
		if (selection != other.selection)
			return false;
		return true;
	}
	
	

}
