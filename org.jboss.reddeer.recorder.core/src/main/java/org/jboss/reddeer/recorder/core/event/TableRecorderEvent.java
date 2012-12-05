package org.jboss.reddeer.recorder.core.event;

import java.util.List;
import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class TableRecorderEvent extends RecorderEvent{
	
	private int index;
	private String group;
	private int items[];
	private List<String> listOfItems;
	
	public TableRecorderEvent(String group,int index, int items[], List<String> listOfItems, String shell, String view){
		this.group=group;
		this.index=index;
		this.items=items;
		this.listOfItems=listOfItems;
		setShellName(shell);
		setViewName(view);
	}
	
	public int getIndex(){
		return index;
	}
	
	public List<String> getListOfItems(){
		return listOfItems;
	}
	
	public int[] getItemsIndex(){
		return items;
	}
	
	public String getGroup(){
		return WidgetUtils.convert(group);
	}

	@Override
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if(group != null){
			testBuilder.append("new DefaultTable(\""+getGroup()+"\","+index);
		} else {
			testBuilder.append("new DefaultTable("+index+")");
		}
		testBuilder.append(".select(");
		if(listOfItems.isEmpty()){
			for(int i=0;i<items.length;i++){
				testBuilder.append(items[i]);
				if(i<items.length-1){
					testBuilder.append(",");
				}
			}
		} else {
			for(int i=0; i<listOfItems.size();i++){
				testBuilder.append("\""+listOfItems.get(i)+"\"");
				if(i<listOfItems.size()-1){
					testBuilder.append(",");
				}
			}
		}
		testBuilder.append(")");
		setOfImports.add(ImportUtils.DEFAULT_TABLE);	
		return setOfImports;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + index;
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
		TableRecorderEvent other = (TableRecorderEvent) obj;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (index != other.index)
			return false;
		return true;
	}
	
	

}
