package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;

public class TreeRule extends GenerationSimpleRule {
	
	private int index;
	private String itemText;
	private String shellName;
	private List<String> parents;
	private String viewName;
	private boolean check;
	private boolean checkDetail;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Tree && event.item instanceof TreeItem && event.type == SWT.Selection;
	}

	@Override
	protected String getActon() {
		if(checkDetail){
			return ".setCheck("+check+")";
		}
		return ".select()";
	}

	@Override
	public void initializeForEvent(Event event) {
		CTabFolder folder = WidgetUtils.getView((Tree)event.widget);
		if(folder != null){
			this.viewName = folder.getSelection().getText();
		}
		this.index = WidgetUtils.getIndex((Tree)event.widget);	
		this.itemText = ((TreeItem)event.item).getText();
		Shell shell = WidgetUtils.getShell((Tree)event.widget);
		if(shell!=null){
			this.shellName = shell.getText();
		}
		
		TreeItem parent = ((TreeItem)event.item).getParentItem();
		parents = new ArrayList<String>();
		while (parent != null) {
			parents.add(parent.getText());
			parent = parent.getParentItem();
		}
		Collections.reverse(parents);
		if(checkDetail = event.detail == SWT.CHECK){
			check = ((TreeItem)event.item).getChecked();
		}
	}

	@Override
	protected String getWidgetAccessor() {
		StringBuilder res = new StringBuilder();
		if(shellName != null){
			res.append("new ShellTreeItem(");
		} else {
			res.append("new ViewTreeItem(");
		}
		if (index != 0) {
			res.append(index+",");
		}
		for(String parent: parents){
			res.append("\""+parent+"\",");
		}
		res.append("\""+itemText+"\")");
		return res.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (check ? 1231 : 1237);
		result = prime * result + index;
		result = prime * result
				+ ((itemText == null) ? 0 : itemText.hashCode());
		result = prime * result + ((parents == null) ? 0 : parents.hashCode());
		result = prime * result
				+ ((shellName == null) ? 0 : shellName.hashCode());
		result = prime * result
				+ ((viewName == null) ? 0 : viewName.hashCode());
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
		TreeRule other = (TreeRule) obj;
		if (check != other.check)
			return false;
		if (index != other.index)
			return false;
		if (itemText == null) {
			if (other.itemText != null)
				return false;
		} else if (!itemText.equals(other.itemText))
			return false;
		if (parents == null) {
			if (other.parents != null)
				return false;
		} else if (!parents.equals(other.parents))
			return false;
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		if (viewName == null) {
			if (other.viewName != null)
				return false;
		} else if (!viewName.equals(other.viewName))
			return false;
		return true;
	}

	@Override
	public String getRuleName() {
		return "Tree rule";
	}

	public String getShellName() {
		return shellName;
	}

	public void setShellName(String shellName) {
		this.shellName = shellName;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	

}
