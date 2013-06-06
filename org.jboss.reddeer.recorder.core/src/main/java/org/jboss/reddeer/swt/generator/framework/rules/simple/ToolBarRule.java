package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class ToolBarRule extends GenerationSimpleRule{
	
	private String toolTipText;
	public static final int WORKBENCH=1;
	public static final int VIEW=2;
	public static final int SHELL=3;
	private int type;
	

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof ToolItem && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.toolTipText = ((ToolItem)event.widget).getToolTipText();
		Shell s = WidgetUtils.getShell(((ToolItem)event.widget).getParent());
		if(s!=null){
			setShellTitle(s.getText());
		}
		CTabItem v = WidgetUtils.getView(((ToolItem)event.widget).getParent());
		Shell workbench = WidgetUtils.getWorkbench();
		if(v!=null){
			setViewTitle(v.getText());
		}
		if(getViewTitle() != null){
			type=VIEW;
		} else if(workbench!= null && getShellTitle() != workbench.getText()){
			type=SHELL;
		} else {
			type=WORKBENCH;
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		if(type==WORKBENCH){
			builder.append("new WorkbenchToolItem(");
		} else if (type==VIEW){
			builder.append("new ViewToolItem(");
		} else if(type==SHELL){
			builder.append("new ShellToolItem(");
		}
		builder.append("\""+toolTipText+"\").click()");
		toReturn.add(builder.toString());
		return toReturn;
		
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((toolTipText == null) ? 0 : toolTipText.hashCode());
		result = prime * result + type;
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
		ToolBarRule other = (ToolBarRule) obj;
		if (toolTipText == null) {
			if (other.toolTipText != null)
				return false;
		} else if (!toolTipText.equals(other.toolTipText))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String getImport() {
		if(type==WORKBENCH){
			return "org.jboss.reddeer.swt.impl.toolbar.WorkbenchToolItem";
		} else if (type==VIEW){
			return "org.jboss.reddeer.swt.impl.toolbar.ViewToolItem";
		} else if(type==SHELL){
			return "org.jboss.reddeer.swt.impl.toolbar.ShellToolItem";
		}
		return null;
	}
	
	
}
