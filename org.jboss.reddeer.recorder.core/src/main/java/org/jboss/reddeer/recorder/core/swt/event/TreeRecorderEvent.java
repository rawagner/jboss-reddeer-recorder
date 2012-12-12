package org.jboss.reddeer.recorder.core.swt.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.reddeer.recorder.core.util.ImportUtils;
import org.jboss.reddeer.recorder.core.util.WidgetUtils;

public class TreeRecorderEvent extends RecorderEvent {

	private String text;
	private int index;
	private List<String> parents;
	private boolean reverseContextTreeHack = false;
	private int itemIndex;
	private boolean  hasDuplicates;
	private boolean addedTreeIndex=false;
	
	public TreeRecorderEvent(String text,int index,int itemIndex,List<String> parents, String shell, String view, boolean hasDuplicates){
		this.text=text;
		this.index=index;
		this.parents=parents;
		this.itemIndex=itemIndex;
		this.hasDuplicates=hasDuplicates;
		setShellName(shell);
		setViewName(view);
	}

	public List<String> getParents() {
		List<String> convertedParents = new ArrayList<String>();
		for(String s: parents){
			convertedParents.add(WidgetUtils.convert(s));
		}
		return convertedParents;
	}

	public String getText() {
		return WidgetUtils.convert(text);
	}

	
	public Set<String> start(StringBuilder testBuilder, RecorderEvent previousEvent) {
		if(reverseContextTreeHack){
			return setOfImports;
		}
		if(getShellName() != null){
			setOfImports.add(ImportUtils.SHELL_TREE_ITEM);
			if(index == 0){
				testBuilder.append("new ShellTreeItem(");
			} else {
				testBuilder.append("new ShellTreeItem("+index+",");
				addedTreeIndex=true;
			}
		} else{
			setOfImports.add(ImportUtils.VIEW_TREE_ITEM);
			if(index == 0){
				testBuilder.append("new ViewTreeItem(");
			} else {
				testBuilder.append("new ViewTreeItem("+index+",");
				addedTreeIndex=true;
			}
		}
		if(itemIndex >0){
			if(addedTreeIndex){
				testBuilder.append(itemIndex + ",");
			}else{
				testBuilder.append("0,"+itemIndex + ",");
			}
		}
		for (String p : getParents()) {
			testBuilder.append("\"" + p + "\",");
		}
		testBuilder.append("\""+getText()+"\")");
		return setOfImports;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		TreeRecorderEvent test = (TreeRecorderEvent) obj;
		return getText().equals(test.getText()) && (parents.equals(test.getParents()));
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + text.hashCode() + parents.hashCode();
		return hash;
	}

	public int getIndex() {
		return index;
	}
	
	public int getItemIndex(){
		return itemIndex;
	}
	
	public boolean hasDuplicates(){
		return hasDuplicates;
	}

	public boolean isReverseContextTreeHack() {
		return reverseContextTreeHack;
	}

	public void setReverseContextTreeHack(boolean reverseContextTreeHack) {
		this.reverseContextTreeHack = reverseContextTreeHack;
	}

}
