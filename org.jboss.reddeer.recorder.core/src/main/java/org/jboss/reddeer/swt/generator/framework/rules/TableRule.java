package org.jboss.reddeer.swt.generator.framework.rules;

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

public class TableRule extends GenerationSimpleRule {
	
	private String group;
	private int index;
	private int items[];
	private List<String> listOfSelectedItems = new ArrayList<String>();
	private String shellName;
	private boolean check;
	private boolean checkDetail;

	@Override
	public boolean appliesTo(Event event) {
		return event.widget instanceof Table && event.type == SWT.Selection;
	}

	@Override
	public void initializeForEvent(Event event) {
		Table table = (Table)event.widget;
		if(!tableHasDuplicates(table.getItems())){
			for(TableItem item: table.getSelection()){
				this.listOfSelectedItems.add(item.getText());
			}
		} else {
			this.items = table.getSelectionIndices(); 
		}
		this.group = WidgetUtils.getGroup(table);
		this.index = WidgetUtils.getIndex(table);
		Shell shell = WidgetUtils.getShell(table);
		if(shell != null){
			this.shellName = shell.getText();
		}
		if(checkDetail = event.detail == SWT.CHECK){
			check = table.getSelection()[0].getChecked();
		}
		
	}

	@Override
	protected String getWidgetAccessor() {
		StringBuilder builder  = new StringBuilder();
		builder.append("new DefaultTable(");
		if(group != null){
			builder.append("\""+group+"\",");
		}
		if(index > 0){
			builder.append(index);
		}
		builder.append(")");
		return builder.toString();
	}

	@Override
	protected String getActon() {
		StringBuilder builder  = new StringBuilder();
		if(checkDetail){
			if(listOfSelectedItems.isEmpty()){ //only one item can be checked..so pick 0 always
				return ".check("+items[0]+")";
			} else {
				return ".check(\"" +listOfSelectedItems.get(0)+ "\")";
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
		return builder.toString();
	}

	@Override
	public String getRuleName() {
		return "table rule";
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

	public String getShellName() {
		return shellName;
	}

	public void setShellName(String shellName) {
		this.shellName = shellName;
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
				+ ((shellName == null) ? 0 : shellName.hashCode());
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
		if (shellName == null) {
			if (other.shellName != null)
				return false;
		} else if (!shellName.equals(other.shellName))
			return false;
		return true;
	}

	

}
