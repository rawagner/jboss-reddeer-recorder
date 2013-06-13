package org.jboss.reddeer.swt.generator.framework.rules.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;
import org.eclipse.swtbot.generator.framework.WidgetUtils;
import org.eclipse.ui.forms.widgets.Section;
import org.jboss.reddeer.swt.generator.framework.rules.RuleUtils;

public class TableRule extends GenerationSimpleRule {
	
	private String group;
	private int index;
	private int items[];
	private List<String> listOfSelectedItems = new ArrayList<String>();
	private boolean check;
	private boolean checkDetail;
	private String section;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Table && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		Table table = (Table)event.widget;
		if(!tableHasDuplicates(table.getItems())){
			for(TableItem item: table.getSelection()){
				this.listOfSelectedItems.add(WidgetUtils.cleanText(item.getText()));
			}
		} else {
			this.items = table.getSelectionIndices(); 
		}
		this.group = WidgetUtils.getGroup(table);
		this.index = WidgetUtils.getIndex(table);
		Shell s = WidgetUtils.getShell((Table)event.widget);
		if(s!=null){
			setShellTitle(s.getText());
		}
		if(checkDetail = event.detail == SWT.CHECK){
			check = table.getSelection()[0].getChecked();
		}
		Section sec = WidgetUtils.getSection((Table)event.widget);
		if(sec!=null){
			setSection(sec.getText());
		}
		
	}

	@Override
	public List<String> getActions() {
		List<String> toReturn = new ArrayList<String>();
		StringBuilder builder  = new StringBuilder();
		if(section!=null){
			toReturn.add(RuleUtils.getSectionRule(section));
		}
		builder.append("new DefaultTable(");
		if(group != null){
			builder.append("\""+group+"\",");
		}
		if(index > 0){
			builder.append(index);
		}
		builder.append(")");
		if(checkDetail){
			if(listOfSelectedItems.isEmpty()){ //only one item can be checked..so pick 0 always
				builder.append(".check("+items[0]+")");
			} else {
				builder.append(".check(\"" +listOfSelectedItems.get(0)+ "\")");
			}
			
		} else {
			builder.append(".select(");
			if(listOfSelectedItems.isEmpty()){
				for(int i=0;i<items.length;i++){
					builder.append(items[i]);
					if(i<items.length-1){
						builder.append(",");
					}
				}
			} else {
				for(int i=0; i<listOfSelectedItems.size();i++){
					builder.append("\""+listOfSelectedItems.get(i)+"\"");
					if(i<listOfSelectedItems.size()-1){
						builder.append(",");
					}
				}
			}
		}
		builder.append(")");
		toReturn.add(builder.toString());
		return toReturn;
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

	public List<String> getListOfSelectedItems() {
		return listOfSelectedItems;
	}

	public void setListOfSelectedItems(List<String> listOfSelectedItems) {
		this.listOfSelectedItems = listOfSelectedItems;
	}
	
	private boolean tableHasDuplicates(TableItem tableItems[]){
		Set<String> setOfStrings = new HashSet<String>();
		for(TableItem item: tableItems){
			if(!setOfStrings.add(item.getText())){
				return true;
			}
			
		}
		return false;
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
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
		result = prime * result + Arrays.hashCode(items);
		result = prime
				* result
				+ ((listOfSelectedItems == null) ? 0 : listOfSelectedItems
						.hashCode());
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
		TableRule other = (TableRule) obj;
		if (check != other.check)
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (index != other.index)
			return false;
		if (!Arrays.equals(items, other.items))
			return false;
		if (listOfSelectedItems == null) {
			if (other.listOfSelectedItems != null)
				return false;
		} else if (!listOfSelectedItems.equals(other.listOfSelectedItems))
			return false;
		if (getShellTitle() == null) {
			if (other.getShellTitle() != null)
				return false;
		} else if (!getShellTitle().equals(other.getShellTitle()))
			return false;
		return true;
	}

	
	@Override
	public List<String> getImports() {
		List<String> toReturn = new ArrayList<String>();
		toReturn.add("org.jboss.reddeer.swt.impl.table.DefaultTable");
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
