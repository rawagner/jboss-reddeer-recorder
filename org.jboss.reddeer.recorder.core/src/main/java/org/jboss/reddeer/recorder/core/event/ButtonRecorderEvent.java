package org.jboss.reddeer.recorder.core.event;

import java.util.Set;

import org.eclipse.swt.SWT;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class ButtonRecorderEvent extends RecorderEvent {
	
	
	private String text;
	private int type;
	private boolean selected;
	private int index;
	private String group;

	public ButtonRecorderEvent(String text, String group, boolean selected,
			int index, int type, String shell, String view) {
		this.text = text;
		this.index = index;
		this.type = type;
		this.group = group;
		this.selected = selected;
		setShellName(shell);
		setViewName(view);
	}

	public String getText() {
		return WidgetUtils.convert(text);
	}

	public int getType() {
		return type;
	}

	public boolean isSelected() {
		return selected;
	}

	public int getIndex() {
		return index;
	}

	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if (group == null) {
			if (text.equals("") || text == null) {
				if ((type & SWT.PUSH) != 0) {
					testBuilder.append("new PushButton(" + index + ")");
				} else if ((type & SWT.CHECK) != 0) {
					testBuilder.append("new CheckBox(" + index + ")");
				} else if ((type & SWT.ARROW) != 0) {
					testBuilder.append("bot.arrowButton(" + index + ")");
				} else if ((type & SWT.RADIO) != 0) {
					testBuilder.append("new RadioButton(" + index + ")");
				} else if ((type & SWT.TOGGLE) != 0) {
					testBuilder.append("bot.toggleButton(" + index + ")");
				}
			} else {
				if ((type & SWT.PUSH) != 0) {
					testBuilder.append("new PushButton(\"" + getText() + "\")");
				} else if ((type & SWT.CHECK) != 0) {
					testBuilder.append("new CheckBox(\"" + getText() + "\")");
				} else if ((type & SWT.ARROW) != 0) {
					testBuilder
							.append("bot.arrowButton(\"" + getText() + "\")");
				} else if ((type & SWT.RADIO) != 0) {
					testBuilder
							.append("new RadioButton(\"" + getText() + "\")");
				} else if ((type & SWT.TOGGLE) != 0) {
					testBuilder.append("bot.toggleButton(\"" + getText()
							+ "\")");
				}
			}
		} else {
			if (text.equals("") || text == null) {
				if ((type & SWT.PUSH) != 0) {
					testBuilder.append("new PushButton(\"" + getGroup() + "\","
							+ index + ")");
				} else if ((type & SWT.CHECK) != 0) {
					testBuilder.append("new CheckBox(\"" + getGroup() + "\","
							+ index + ")");
				} else if ((type & SWT.ARROW) != 0) {
					testBuilder.append("bot.arrowButton(" + index + ")");
				} else if ((type & SWT.RADIO) != 0) {
					testBuilder.append("new RadioButton(\"" + getGroup() + "\","
							+ index + ")");
				} else if ((type & SWT.TOGGLE) != 0) {
					testBuilder.append("bot.toggleButton(" + index + ")");
				}
			} else {
				if ((type & SWT.PUSH) != 0) {
					testBuilder.append("new PushButton(\"" + getGroup() + "\",\""
							+ getText() + "\")");
				} else if ((type & SWT.CHECK) != 0) {
					testBuilder.append("new CheckBox(\"" + getGroup() + "\",\""
							+ getText() + "\")");
				} else if ((type & SWT.ARROW) != 0) {
					testBuilder
							.append("bot.arrowButton(\"" + getText() + "\")");
				} else if ((type & SWT.RADIO) != 0) {
					testBuilder.append("new RadioButton(\"" + getGroup()
							+ "\",\"" + getText() + "\")");
				} else if ((type & SWT.TOGGLE) != 0) {
					testBuilder.append("bot.toggleButton(\"" + getText()
							+ "\")");
				}
			}
		}
		if ((type & SWT.PUSH) != 0){
			setOfImports.add(ImportUtils.PUSH_BUTTON);
		} else if ((type & SWT.CHECK) != 0) {
			setOfImports.add(ImportUtils.CHECK_BUTTON);
		} else if ((type & SWT.ARROW) != 0) {
			setOfImports.add(ImportUtils.ARROW_BUTTON);
		} else if ((type & SWT.RADIO) != 0) {
			setOfImports.add(ImportUtils.RADIO_BUTTON);
		} else if ((type & SWT.TOGGLE) != 0) {
			setOfImports.add(ImportUtils.TOGGLE_BUTTON);
		}
		return setOfImports;

	}

	public String getGroup() {
		return WidgetUtils.convert(group);
	}

}
