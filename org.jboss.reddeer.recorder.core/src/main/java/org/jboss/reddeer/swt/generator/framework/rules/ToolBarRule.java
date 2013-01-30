package org.jboss.reddeer.swt.generator.framework.rules;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class ToolBarRule extends GenerationSimpleRule{
	
	private String toolTipText;
	private boolean workbenchItem;
	private String viewName;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof ToolItem && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		this.toolTipText = ((ToolItem)event.widget).getToolTipText();
		
		
		CTabFolder folder = WidgetUtils.getView(((ToolItem)event.widget).getParent());
		if(folder != null){
			this.viewName = folder.getSelection().getText();
			workbenchItem = false;
		} else {
			workbenchItem = true;
		}
	}

	@Override
	protected String getActon() {
		return ".click()";
		
	}
	
	@Override
	protected String getWidgetAccessor() {
		StringBuilder builder = new StringBuilder();
		if(workbenchItem){
			builder.append("new WorkbenchToolItem(");
		} else {
			builder.append("new ViewToolItem(");
		}
		builder.append("\""+toolTipText+"\")");
		return builder.toString();
	}

	@Override
	public String getRuleName() {
		return "toolbar rule";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((toolTipText == null) ? 0 : toolTipText.hashCode());
		result = prime * result
				+ ((viewName == null) ? 0 : viewName.hashCode());
		result = prime * result + (workbenchItem ? 1231 : 1237);
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
		if (viewName == null) {
			if (other.viewName != null)
				return false;
		} else if (!viewName.equals(other.viewName))
			return false;
		if (workbenchItem != other.workbenchItem)
			return false;
		return true;
	}

	public String getToolTipText() {
		return toolTipText;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public boolean isWorkbenchItem() {
		return workbenchItem;
	}

	public void setWorkbenchItem(boolean workbenchItem) {
		this.workbenchItem = workbenchItem;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
