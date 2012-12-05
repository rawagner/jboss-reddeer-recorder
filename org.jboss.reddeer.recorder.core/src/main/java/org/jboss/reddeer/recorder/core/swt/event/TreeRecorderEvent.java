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
	
	public TreeRecorderEvent(String text,int index,List<String> parents, String shell, String view){
		this.text=text;
		this.index=index;
		this.parents=parents;
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
			if(index == 0){
				testBuilder.append("new ShellTreeItem(");
			} else {
				testBuilder.append("new ShellTreeItem("+index+",");
			}
		} else{
			if(index == 0){
				testBuilder.append("new ViewTreeItem(");
			} else {
				testBuilder.append("new ViewTreeItem("+index+",");
			}
		}
		for (String p : getParents()) {
			testBuilder.append("\"" + p + "\",");
		}
		testBuilder.append("\""+getText()+"\")");
		setOfImports.add(ImportUtils.DEFAULT_TREE_ITEM);
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

	public boolean isReverseContextTreeHack() {
		return reverseContextTreeHack;
	}

	public void setReverseContextTreeHack(boolean reverseContextTreeHack) {
		this.reverseContextTreeHack = reverseContextTreeHack;
	}

}
