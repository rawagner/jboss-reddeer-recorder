package org.jboss.reddeer.swt.generator.framework.rules;


import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class ButtonRule extends GenerationSimpleRule {

	private String text;
	private int index;
	private String group;
	private String shellName;
	private int style;
	private boolean toggle;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean getToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getShellName() {
		return shellName;
	}

	public void setShellName(String shellName) {
		this.shellName = shellName;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Button && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.text = ((Button)event.widget).getText();
		this.group = WidgetUtils.getGroup((Button)event.widget);
		this.index = WidgetUtils.getIndex((Button)event.widget);
		this.shellName = WidgetUtils.getShell((Button)event.widget).getText();
		this.style = ((Button)event.widget).getStyle();
		if((style & SWT.CHECK) != 0){
			this.toggle=((Button)event.widget).getSelection();
		}
		
		
	}

	@Override
	protected String getWidgetAccessor() {
		StringBuilder builder = new StringBuilder();
		if((style & SWT.PUSH)!=0){
			builder.append("new PushButton(");
		} else if((style & SWT.CHECK)!=0){
			builder.append("new CheckBox(");
		} else if((style & SWT.ARROW)!=0){
			builder.append("new Arrow//(");
		} else if((style & SWT.RADIO)!=0){
			builder.append("new RadioButton(");
		} else if((style & SWT.TOGGLE)!=0){
			builder.append("new Toggle//(");
		}
		if(group != null){
			builder.append("\""+group+"\",");
		}
		if(text == null || text.isEmpty()){
			builder.append(index);
		} else {
			builder.append("\""+text+"\"");
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	protected String getActon() {
		if((style & SWT.CHECK)!=0){
			return ".toggle("+toggle+")";
		}
		return ".click()";
	}
	
	@Override
	public String getRuleName() {
		return "Button rule";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result
				+ ((shellName == null) ? 0 : shellName.hashCode());
		result = prime * result + style;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + (toggle ? 1231 : 1237);
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
		ButtonRule other = (ButtonRule) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (index != other.index)
			return false;
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		if ((style & other.style) == 0)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (toggle != other.toggle)
			return false;
		return true;
	}
	
	

}
