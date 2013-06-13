package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.generator.framework.rules.RuleUtils;

public class TreeRule extends GenerationSimpleRule {
	
	private int treeIndex;
	private String itemText;
	private List<String> parents;
	private boolean check;
	private boolean checkDetail;
	private String section;
	
	public int getTreeIndex() {
		return treeIndex;
	}

	public void setTreeIndex(int treeIndex) {
		this.treeIndex = treeIndex;
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
	public void initializeForEvent(Event event) {
		this.treeIndex = WidgetUtils.getIndex((Tree)event.widget);	
		this.itemText = WidgetUtils.cleanText(((TreeItem)event.item).getText());
		Shell s = WidgetUtils.getShell((Tree)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		CTabItem v = WidgetUtils.getView((Tree)event.widget);
		if(v!=null){
			setViewTitle(v.getText());
		}
		//((Tree)event.widget).getItems() check if there are the same items - if are, then use index
		TreeItem parent = ((TreeItem)event.item).getParentItem();
		parents = new ArrayList<String>();
		while (parent != null) {
			parents.add(WidgetUtils.cleanText(parent.getText()));
			parent = parent.getParentItem();
		}
		Collections.reverse(parents);
		if(checkDetail = event.detail == SWT.CHECK){
			check = ((TreeItem)event.item).getChecked();
		}
		Section sec = WidgetUtils.getSection((Hyperlink)event.widget);
		if(sec!=null){
			setSection(sec.getText());
		}
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder res = new StringBuilder();
		if(section!=null){
			toReturn.add(RuleUtils.getSectionRule(section));
		}
		res.append("new DefaultTreeItem(");
		if (treeIndex != 0) {
			res.append(treeIndex+",");
		}
		for(String parent: parents){
			res.append("\""+parent+"\",");
		}
		res.append("\""+itemText+"\")");
		if(checkDetail){
			res.append(".setCheck("+check+")");
		} else {
			res.append(".select()");
		}
		toReturn.add(res.toString());
		return toReturn;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (check ? 1231 : 1237);
		result = prime * result + treeIndex;
		result = prime * result
				+ ((itemText == null) ? 0 : itemText.hashCode());
		result = prime * result
				+ ((getShellTitle() == null) ? 0 : getShellTitle().hashCode());
		result = prime * result
				+ ((getViewTitle() == null) ? 0 : getViewTitle().hashCode());
		result = prime * result + ((parents == null) ? 0 : parents.hashCode());
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
		if (treeIndex != other.treeIndex)
			return false;
		if (itemText == null) {
			if (other.itemText != null)
				return false;
		} else if (!itemText.equals(other.itemText))
			return false;
		if (getShellTitle() == null) {
			if (other.getShellTitle() != null)
				return false;
		} else if (!getShellTitle().equals(other.getShellTitle()))
			return false;
		if (getViewTitle() == null) {
			if (other.getViewTitle() != null)
				return false;
		} else if (!getViewTitle().equals(other.getViewTitle()))
			return false;
		if (parents == null) {
			if (other.parents != null)
				return false;
		} else if (!parents.equals(other.parents))
			return false;
		return true;
	}

	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.tree.DefaultTreeItem");
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
