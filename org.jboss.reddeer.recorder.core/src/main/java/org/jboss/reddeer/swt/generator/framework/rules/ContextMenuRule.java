package org.jboss.reddeer.swt.generator.framework.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swtbot.generator.framework.GenerationSimpleRule;

public class ContextMenuRule extends GenerationSimpleRule{
	
	private List<String> path;
	private String menu;

	@Override
	public boolean appliesTo(Event event) {
		boolean menu = event.widget instanceof MenuItem;
		int style = 0;
		if(menu){
			MenuItem currentItem = ((MenuItem)event.widget);
			Menu parent = null;
			while (currentItem != null && (parent = currentItem.getParent()) != null) {
				style = parent.getStyle();
				currentItem = parent.getParentItem();
			}
		}
		return event.type == SWT.Selection && menu && (style & SWT.POP_UP)!=0;
	}

	@Override
	public void initializeForEvent(Event event) {
		MenuItem item = (MenuItem) event.widget;
		menu = cleanMenuText(item.getText());
		path = new ArrayList<String>();
		MenuItem currentItem = item;
		Menu parent = null;
		while (currentItem != null && (parent = currentItem.getParent()) != null) {
			currentItem = parent.getParentItem();
			if (currentItem != null && currentItem.getText() != null) {
				path.add(cleanMenuText(currentItem.getText()));
			}
		}
		Collections.reverse(path);
	}

	@Override
	protected String getWidgetAccessor() {
		StringBuilder code = new StringBuilder();
		code.append("new ContextMenu(");
		for (String text : path) {
			code.append("\""+text+"\",");
		}
		code.append("\""+menu+"\")");
		return code.toString();
	}

	@Override
	protected String getActon() {
		return ".select()";
	}

	@Override
	public String getRuleName() {
		return "context menu";
	}
	
	private static String cleanMenuText(String text) {
		return text.replace("&", "").split("\t")[0];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		ContextMenuRule other = (ContextMenuRule) obj;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	

}
